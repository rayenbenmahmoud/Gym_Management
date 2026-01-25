let currentUserRole = null;
let selectedAdherentId = null;

// ==================
// Définition des éléments HTML
// ==================
const adherentForm = document.getElementById("adherentForm");
const adherentId = document.getElementById("adherentId");
const nom = document.getElementById("nom");
const prenom = document.getElementById("prenom");
const email = document.getElementById("email");
const telephone = document.getElementById("telephone");
const dateNaissance = document.getElementById("dateNaissance");

const adherentModal = document.getElementById("adherentModal");
const formTitle = document.getElementById("formTitle");

const cardModal = document.getElementById("cardModal");
const cardSolde = document.getElementById("cardSolde");
const rechargeAmount = document.getElementById("rechargeAmount");

// ==================
// Initialisation après chargement DOM
// ==================
document.addEventListener("DOMContentLoaded", async () => {
    await loadCurrentUser();
    await loadAdherents();
    setMaxBirthDate();
});

// ==================
// Chargement utilisateur
// ==================
async function loadCurrentUser() {
    const response = await fetch(`/auth/me`, {
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
        document.getElementById("rechargeSection").style.display = "none";
    }
}

// ==================
// Date max pour date de naissance
// ==================
function setMaxBirthDate() {
    const today = new Date().toISOString().split("T")[0];
    document.getElementById("dateNaissance").max = today;
}

// ==================
// Chargement des adhérents
// ==================
async function loadAdherents() {
    const response = await fetch(`/api/adherents`, {
        credentials: "include"
    });

    if (response.status === 401) {
        location.href = "index.html";
        return;
    }

    const adherents = await response.json();
    const table = document.getElementById("adherentsTable");
    table.innerHTML = "";

    adherents.forEach(a => {
        table.innerHTML += `
        <tr>
            <td>${a.nom}</td>
            <td>${a.prenom}</td>
            <td>${a.email}</td>
            <td>${a.telephone}</td>
            <td>${a.dateNaissance}</td>
            <td>
                <a href="#" onclick="openCardModal(${a.id})">
                    #${a.cardNumber}
                </a>
            </td>
            <td>
                ${
                    currentUserRole === "ADMIN"
                        ? `
                          <button onclick="editAdherent(${a.id})">Modifier</button>
                          <button onclick="deleteAdherent(${a.id})">Supprimer</button>
                          `
                        : ""
                }
            </td>
        </tr>`;
    });
}

// ==================
// Formulaire ajout adhérent
// ==================
function showAddForm() {
    if (currentUserRole !== "ADMIN") return;
    adherentForm.reset();
    adherentId.value = "";
    formTitle.innerText = "Ajouter un adhérent";
    adherentModal.style.display = "flex";
}

// ==================
// Masquer formulaire
// ==================
function hideForm() {
    adherentModal.style.display = "none";
}

// ==================
// Sauvegarder adhérent
// ==================
async function saveAdherent(event) {
    event.preventDefault();

    const id = adherentId.value;
    const data = {
        nom: nom.value,
        prenom: prenom.value,
        email: email.value,
        telephone: telephone.value,
        dateNaissance: dateNaissance.value
    };

    if (id) {
        // modification
        await fetch(`/api/adherents/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(data)
        });
    } else {
        // ajout
        await fetch(`/api/adherents`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(data)
        });
    }

    hideForm();
    loadAdherents();
}

// ==================
// Modal Carte
// ==================
async function openCardModal(adherentId) {
    selectedAdherentId = adherentId;
    cardModal.style.display = "flex";
    loadSolde(adherentId);
    loadRecharges(adherentId);
}

function closeCardModal() {
    cardModal.style.display = "none";
}

async function loadSolde(adherentId) {
    const res = await fetch(`/api/cards/solde/${adherentId}`, {
        credentials: "include"
    });
    const solde = await res.json();
    cardSolde.innerText = solde;
}

async function loadRecharges(adherentId) {
    const res = await fetch(`/api/cards/recharges/${adherentId}`, {
        credentials: "include"
    });

    const recharges = await res.json();
    const tbody = document.getElementById("rechargeList");
    tbody.innerHTML = "";

    if (recharges.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="2">Aucune recharge</td>
            </tr>`;
        return;
    }

    recharges.forEach(r => {
        tbody.innerHTML += `
            <tr>
                <td>${new Date(r.date).toLocaleDateString()}</td>
                <td>${r.amount}</td>
            </tr>`;
    });
}

async function rechargeCard() {
    const amount = rechargeAmount.value;

    if (!amount || amount <= 0) {
        alert("Montant invalide");
        return;
    }

    await fetch(
        `/api/cards/recharge/${selectedAdherentId}?montant=${amount}`,
        {
            method: "POST",
            credentials: "include"
        }
    );

    rechargeAmount.value = "";
    loadSolde(selectedAdherentId);
    loadRecharges(selectedAdherentId);
}

// ==================
// Edit adhérent
// ==================
async function editAdherent(id) {
    if (currentUserRole !== "ADMIN") {
        alert("Accès refusé");
        return;
    }

    const response = await fetch(`/api/adherents/${id}`, {
        credentials: "include"
    });

    const a = await response.json();

    adherentId.value = a.id;
    nom.value = a.nom;
    prenom.value = a.prenom;
    email.value = a.email;
    telephone.value = a.telephone;
    dateNaissance.value = a.dateNaissance;

    formTitle.innerText = "Modifier l'adhérent";
    adherentModal.style.display = "flex";
}

// ==================
// Supprimer adhérent
// ==================
async function deleteAdherent(id) {
    if (currentUserRole !== "ADMIN") {
        alert("Accès refusé");
        return;
    }

    if (!confirm("Confirmer la suppression ?")) return;

    await fetch(`/api/adherents/${id}`, {
        method: "DELETE",
        credentials: "include"
    });

    loadAdherents();
}
