/**
 * Cette méthode permet de déconnecter l'utilisateur
 * et de rediriger vers la page de connexion.
 */
document.getElementById("logoutBtn").addEventListener("click", logout);

async function logout() {

    await fetch(`/auth/logout`, {
        method: "POST",
        credentials: "include"
    });

    window.location.href = "index.html";
}
