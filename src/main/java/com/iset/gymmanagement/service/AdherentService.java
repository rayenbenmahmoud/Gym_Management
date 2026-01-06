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

    // 1️⃣ Ajouter un adhérent
    public Adherent addAdherent(Adherent adherent) {

        // حفظ المشترك أولًا
        Adherent savedAdherent = adherentRepository.save(adherent);

        // إنشاء بطاقة جديدة
        Card card = new Card();
        card.setSolde(BigDecimal.ZERO);

        // ربط البطاقة بالمشترك
        card.setAdherent(savedAdherent);

        // حفظ البطاقة
        cardRepository.save(card);

        return savedAdherent;
    }

    // 2️⃣ Modifier un adhérent
    public Adherent updateAdherent(Long id, Adherent updated) {

        Adherent existing = adherentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas."));

        existing.setNom(updated.getNom());
        existing.setPrenom(updated.getPrenom());
        existing.setEmail(updated.getEmail());
        existing.setTelephone(updated.getTelephone());

        return adherentRepository.save(existing);
    }

    // 3️⃣ Supprimer un adhérent
    public void deleteAdherent(Long id) {

        if (!adherentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas.");
        }

        adherentRepository.deleteById(id);
    }

    // 4️⃣ Liste des adhérents
    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
    }

    // 5️⃣ Détail d’un adhérent
    public Adherent getAdherentById(Long id) {
        return adherentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adhérent avec l'id " + id + " n'existe pas."));
    }
}
