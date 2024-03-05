package com.example.wordlyweek.service;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;

@Service
public class WriterJpaService implements WriterRepository {
    @Autowired
    public WriterJpaRepository writerJpaRepository;

    @Autowired
    public MagazineJpaRepository magazineJpaRepository;

    @Override
    public ArrayList<Writer> getWriters() {
        List<Writer> writersList = writerJpaRepository.findAll();
        ArrayList<Writer> writers = new ArrayList<>(writersList);
        return writers;
    }

    @Override
    public Writer addWriter(Writer writer) {
        try {
            List<Integer> magazineIds = new ArrayList<>();
            for (Magazine magazine : writer.getMagazines()) {
                magazineIds.add(magazine.getMagazineId());
            }
            List<Magazine> magazinesList = magazineJpaRepository.findAllById(magazineIds);
            if (magazinesList.size() != magazineIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            writer.setMagazines(magazinesList);

            return writerJpaRepository.save(writer);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Writer getWriterById(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Writer updateWriter(int writerId, Writer writer) {
        try {
            Writer newWriter = writerJpaRepository.findById(writerId).get();
            if (writer.getWriterName() != null) {
                newWriter.setWriterName(writer.getWriterName());
            }
            if (writer.getBio() != null) {
                newWriter.setBio(writer.getBio());
            }
            if (writer.getMagazines() != null) {
                List<Integer> magazineIds = new ArrayList<>();

                for (Magazine magazine : writer.getMagazines()) {
                    magazineIds.add(magazine.getMagazineId());
                }

                List<Magazine> magazinesList = magazineJpaRepository.findAllById(magazineIds);

                if (magazinesList.size() != magazineIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newWriter.setMagazines(magazinesList);
            }
            return writerJpaRepository.save(newWriter);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteWriter(int writerId) {
        try {
            writerJpaRepository.deleteById(writerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Magazine> getWriterMagazines(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer.getMagazines();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}