let currentUserRole = null;

// ==================
// Définition des éléments HTML
// ==================
const productForm = document.getElementById("productForm");
const productId = document.getElementById("productId");
const nom = document.getElementById("nom");
const description = document.getElementById("description");
const prix = document.getElementById("prix");
const quantiteStock = document.getElementById("quantiteStock");
const imageFile = document.getElementById("imageFile");

const productModal = document.getElementById("productModal");
const formTitle = document.getElementById("formTitle");

// ==================
// Initialisation après chargement DOM
// ==================
document.addEventListener("DOMContentLoaded", async () => {
    await loadCurrentUser();
    await loadProducts();
});

// ==================
// Chargement utilisateur
// ==================
async function loadCurrentUser() {
    const response = await fetch(`${BASE_URL}/auth/me`, {
        credentials: "include"
    });

    if (!response.ok) {
        location.href = "index.html";
        return;
    }

    const user = await response.json();
    currentUserRole = user.role;
    applyPermissions();
}

// ==================
// Permissions selon rôle
// ==================
function applyPermissions() {
    const addBtn = document.getElementById("addBtn");
    const actions = document.getElementById("actions");

    if (currentUserRole !== "ADMIN") {
        if (addBtn) addBtn.style.display = "none";
        if (actions) actions.style.display = "none";
    }
}

// ==================
// Chargement des produits
// ==================
async function loadProducts() {
    const response = await fetch(`${BASE_URL}/api/products`, {
        credentials: "include"
    });

    if (response.status === 401) {
        location.href = "index.html";
        return;
    }

    const products = await response.json();
    const table = document.getElementById("productsTable");
    table.innerHTML = "";

    products.forEach(p => {
        table.innerHTML += `
        <tr>
            <td>${p.nom}</td>
            <td>${p.description ?? ""}</td>
            <td>${p.prix}</td>
            <td>${p.quantiteStock}</td>
            <td>
                ${
                    p.image
                        ? `<img src="${BASE_URL}${p.image}" width="50">`
                        : ""
                }
            </td>
            <td>
                ${
                    currentUserRole === "ADMIN"
                        ? `
                          <button onclick="editProduct(${p.id})">Modifier</button>
                          <button onclick="deleteProduct(${p.id})">Supprimer</button>
                          `
                        : ""
                }
            </td>
        </tr>`;
    });
}

// ==================
// Formulaire ajout produit
// ==================
function showAddForm() {
    if (currentUserRole !== "ADMIN") return;
    productForm.reset();
    productId.value = "";
    formTitle.innerText = "Ajouter un produit";
    productModal.style.display = "flex";
}

// ==================
// Masquer formulaire
// ==================
function hideForm() {
    productModal.style.display = "none";
}

// ==================
// Sauvegarder produit
// ==================
async function saveProduct(event) {
    event.preventDefault();

    const id = productId.value;

    const formData = new FormData();
    formData.append("nom", nom.value);
    formData.append("description", description.value);
    formData.append("prix", prix.value);
    formData.append("quantiteStock", quantiteStock.value);

    if (imageFile.files.length > 0) {
        formData.append("imageFile", imageFile.files[0]);
    }

    if (id) {
        // modification
        await fetch(`${BASE_URL}/api/products/${id}`, {
            method: "PUT",
            credentials: "include",
            body: formData
        });
    } else {
        // ajout
        await fetch(`${BASE_URL}/api/products`, {
            method: "POST",
            credentials: "include",
            body: formData
        });
    }

    hideForm();
    loadProducts();
}

// ==================
// Edit produit
// ==================
async function editProduct(id) {
    if (currentUserRole !== "ADMIN") {
        alert("Accès refusé");
        return;
    }

    const response = await fetch(`${BASE_URL}/api/products/${id}`, {
        credentials: "include"
    });

    const p = await response.json();

    productId.value = p.id;
    nom.value = p.nom;
    description.value = p.description;
    prix.value = p.prix;
    quantiteStock.value = p.quantiteStock;
    imageFile.value = "";

    formTitle.innerText = "Modifier le produit";
    productModal.style.display = "flex";
}

// ==================
// Supprimer produit
// ==================
async function deleteProduct(id) {
    if (currentUserRole !== "ADMIN") {
        alert("Accès refusé");
        return;
    }

    if (!confirm("Confirmer la suppression ?")) return;

    await fetch(`${BASE_URL}/api/products/${id}`, {
        method: "DELETE",
        credentials: "include"
    });

    loadProducts();
}
