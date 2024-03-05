package com.example.wordlyweek.service;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;

@Service
public class MagazineJpaService implements MagazineRepository {
    @Autowired
    public MagazineJpaRepository magazineJpaRepository;

    @Autowired
    public WriterJpaRepository writerJpaRepository;

    @Override
    public ArrayList<Magazine> getMagazines() {
        List<Magazine> magazinesList = magazineJpaRepository.findAll();
        ArrayList<Magazine> magazines = new ArrayList<>(magazinesList);
        return magazines;
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        try {
            List<Integer> writerIds = new ArrayList<>();
            List<Writer> writersList = magazine.getWriters();
            for (Writer writer : writersList) {
                writerIds.add(writer.getWriterId());
            }
            List<Writer> writers = writerJpaRepository.findAllById(writerIds);
            if (writerIds.size() != writers.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SOME OF WRITERS ARE FOUND");
            }
            for (Writer writer : writers) {
                writer.getMagazines().add(magazine);
            }
            magazine.setWriters(writers);
            Magazine savedMagazine = magazineJpaRepository.save(magazine);
            writerJpaRepository.saveAll(writers);
            return savedMagazine;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();
            if (magazine.getMagazineName() != null) {
                newMagazine.setMagazineName(magazine.getMagazineName());
            }
            if (magazine.getPublicationDate() != null) {
                newMagazine.setPublicationDate(magazine.getPublicationDate());
            }
            if (magazine.getWriters() != null) {
                List<Writer> writers = newMagazine.getWriters();
                for (Writer writer : writers) {
                    writer.getMagazines().remove(newMagazine);
                }
                writerJpaRepository.saveAll(writers);

                List<Integer> writerIds = new ArrayList<>();

                for (Writer writer : magazine.getWriters()) {
                    writerIds.add(writer.getWriterId());
                }

                List<Writer> newWritersList = writerJpaRepository.findAllById(writerIds);

                if (writerIds.size() != newWritersList.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Writer writer : newWritersList) {
                    writer.getMagazines().add(newMagazine);
                }
                writerJpaRepository.saveAll(newWritersList);
                newMagazine.setWriters(newWritersList);
            }
            return magazineJpaRepository.save(newMagazine);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();
            for (Writer writer : writers) {
                writer.getMagazines().remove(magazine);
            }
            writerJpaRepository.saveAll(writers);
            magazineJpaRepository.deleteById(magazineId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Writer> getMagazineWriters(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}