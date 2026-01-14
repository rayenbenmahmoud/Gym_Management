package com.iset.gymmanagement.service;

import com.iset.gymmanagement.entity.Adherent;
import com.iset.gymmanagement.entity.Card;
import com.iset.gymmanagement.exception.ResourceNotFoundException;
import com.iset.gymmanagement.repository.AdherentRepository;
import com.iset.gymmanagement.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdherentService {

    private final AdherentRepository adherentRepository;
    private final CardRepository cardRepository;

    public AdherentService(AdherentRepository adherentRepository,
                           CardRepository cardRepository) {
        this.adherentRepository = adherentRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * Cette méthode ajoute un nouvel adhérent dans la base de données
     * et crée automatiquement une carte associée avec un solde initial égal à zéro.
     * @param adherent l'adhérent à ajouter
     * @return l'adhérent enregistré
     */
    public Adherent addAdherent(Adherent adherent) {

        Adherent savedAdherent = adherentRepository.save(adherent);

        Card card = new Card();
        card.setSolde(BigDecimal.ZERO);

        card.setAdherent(savedAdherent);

        cardRepository.save(card);

        return savedAdherent;
    }

    /**
     * Cette méthode met à jour les informations d'un adhérent existant
     * en utilisant son identifiant. Si l'adhérent n'existe pas, une exception est levée.
     * @param id l'identifiant de l'adhérent à modifier
     * @param updated les nouvelles informations de l'adhérent
     * @return l'adhérent mis à jour
     */
    public Adherent updateAdherent(Long id, Adherent updated) {

        Adherent existing = adherentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas."));

        existing.setNom(updated.getNom());
        existing.setPrenom(updated.getPrenom());
        existing.setEmail(updated.getEmail());
        existing.setTelephone(updated.getTelephone());

        return adherentRepository.save(existing);
    }

    /**
     * Supprime logiquement un adhérent.
     * L'adhérent est marqué comme supprimé (soft delete)
     * et sa carte est désactivée.
     */
    public void deleteAdherent(Long id) {

        Adherent adherent = adherentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas.")
                );

        adherent.setDeleted(true);
        adherentRepository.save(adherent);

        cardRepository.findByAdherent(adherent)
                .ifPresent(card -> {
                    card.setActive(false);
                    cardRepository.save(card);
                });
    }


    /**
     * Cette méthode retourne la liste de tous les adhérents
     * enregistrés dans la base de données.
     * @return liste des adhérents
     */
    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
    }

    /**
     * Cette méthode récupère un adhérent en utilisant son identifiant.
     * Si aucun adhérent n'est trouvé, une exception est levée.
     * @param id l'identifiant de l'adhérent recherché
     * @return l'adhérent correspondant à l'identifiant
     */
    public Adherent getAdherentById(Long id) {
        return adherentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas."));
    }
}
