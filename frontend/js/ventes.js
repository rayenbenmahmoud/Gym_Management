// ==================
// Variables globales
// ==================
const ventesTable = document.getElementById("ventesTable");
const venteModal = document.getElementById("venteModal");
const adherentSelect = document.getElementById("adherentSelect");
const productsContainer = document.getElementById("productsContainer");

// MODAL DETAILS
const detailsModal = document.getElementById("detailsModal");
const detailsContent = document.getElementById("detailsContent");

// ==================
// Initialisation
// ==================
document.addEventListener("DOMContentLoaded", async () => {
    await loadVentes();
    await loadAdherents();
    await loadProducts();
});

// ==================
// Charger toutes les ventes
// ==================
async function loadVentes() {
    const response = await fetch(`${BASE_URL}/api/ventes`, {
        credentials: "include"
    });

    if (!response.ok) return;

    const ventes = await response.json();
    ventesTable.innerHTML = "";

    ventes.forEach(v => {
        ventesTable.innerHTML += `
            <tr>
                <td>${new Date(v.date).toLocaleDateString()}</td>
                <td>${v.adherentNom} ${v.adherentPrenom}</td>
                <td>${v.montantTotal}</td>
                <td>
                    <button onclick='showDetails(${JSON.stringify(v)})'>
                        Voir
                    </button>
                </td>
            </tr>
        `;
    });
}

// ==================
// Charger les adhérents (select)
// ==================
async function loadAdherents() {
    const res = await fetch(`${BASE_URL}/api/adherents`, {
        credentials: "include"
    });

    const adherents = await res.json();
    adherentSelect.innerHTML = "";

    adherents.forEach(a => {
        adherentSelect.innerHTML += `
            <option value="${a.id}">
                ${a.nom} ${a.prenom}
            </option>
        `;
    });
}

// ==================
// Charger produits pour la vente
// ==================
let allProducts = [];

async function loadProducts() {
    const res = await fetch(`${BASE_URL}/api/products`, {
        credentials: "include"
    });

    allProducts = await res.json();
}

// ==================
// Ouvrir / fermer modal vente
// ==================
function showAddForm() {
    venteModal.style.display = "flex";
    productsContainer.innerHTML = "";
    addProductLine();
}

function hideForm() {
    venteModal.style.display = "none";
}

// ==================
// Ajouter une ligne produit
// ==================
function addProductLine() {
    const div = document.createElement("div");
    div.classList.add("product-line");

    let options = allProducts.map(p =>
        `<option value="${p.id}">${p.nom}</option>`
    ).join("");

    div.innerHTML = `
        <select class="productSelect">${options}</select>
        <input type="number" class="quantiteInput" min="1" value="1">
        <button type="button" onclick="this.parentElement.remove()">X</button>
    `;

    productsContainer.appendChild(div);
}

// ==================
// Enregistrer une vente
// ==================
async function saveVente(event) {
    event.preventDefault();

    const produits = [];
    const lines = document.querySelectorAll(".product-line");

    lines.forEach(line => {
        produits.push({
            productId: line.querySelector(".productSelect").value,
            quantite: line.querySelector(".quantiteInput").value
        });
    });

    const data = {
        adherentId: adherentSelect.value,
        produits: produits
    };

    const res = await fetch(`${BASE_URL}/api/ventes`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(data)
    });

    if (!res.ok) {
        alert("Erreur lors de la vente");
        return;
    }

    hideForm();
    loadVentes();
}

// ==================
// Afficher détails vente (MODAL)
// ==================
function showDetails(vente) {
    let rows = vente.produits.map(p => `
        <tr>
            <td>${p.produitNom}</td>
            <td>${p.quantite}</td>
            <td>${p.prixUnitaire} DT</td>
        </tr>
    `).join("");

    detailsContent.innerHTML = `
        <p><strong>Vente: #</strong>${vente.id}</p>
        <p><strong>Date:</strong> ${new Date(vente.date).toLocaleDateString()}</p>
        <p><strong>Client:</strong> ${vente.adherentNom} ${vente.adherentPrenom}</p>

        <table>
            <thead>
                <tr>
                    <th>Produit</th>
                    <th>Quantité</th>
                    <th>Prix unitaire</th>
                </tr>
            </thead>
            <tbody>
                ${rows}
            </tbody>
        </table>
    `;

    detailsModal.style.display = "flex";
}

// ==================
// Fermer modal détails
// ==================
function hideDetails() {
    detailsModal.style.display = "none";
}
