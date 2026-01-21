/**
 * Cette méthode est exécutée dès le chargement de la page.
 * Elle récupère les données du tableau de bord depuis le backend.
 */
document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {

    try {
        const response = await fetch(`${BASE_URL}/api/dashboard`, {
            method: "GET",
            credentials: "include" // nécessaire pour la session
        });

        if (response.status === 401) {
            // Utilisateur non connecté
            window.location.href = "index.html";
            return;
        }

        const data = await response.json();

        // Affichage du total des ventes
        document.getElementById("totalVentes").innerText = data.totalVentes;

        // Affichage des produits les plus vendus
        const list = document.getElementById("topProduits");
        list.innerHTML = "";

        data.topProduits.forEach(produit => {
            const li = document.createElement("li");
            li.innerText = produit.nom + " (" + produit.quantiteVendue + ")";
            list.appendChild(li);
        });

    } catch (error) {
        alert("Erreur lors du chargement du dashboard");
    }
}
