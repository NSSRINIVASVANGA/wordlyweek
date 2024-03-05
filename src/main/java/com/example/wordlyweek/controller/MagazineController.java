package com.example.wordlyweek.controller;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;
import com.example.wordlyweek.service.*;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class MagazineController {
    @Autowired
    public MagazineJpaService magazineJpaService;

    @GetMapping("/magazines")
    public ArrayList<Magazine> getMagazines() {
        return magazineJpaService.getMagazines();
    }

    @PostMapping("/magazines")
    public Magazine addMagazine(@RequestBody Magazine magazine) {
        return magazineJpaService.addMagazine(magazine);
    }

    @GetMapping("/magazines/{magazineId}")
    public Magazine getMagazineById(@PathVariable("magazineId") int magazineId) {
        return magazineJpaService.getMagazineById(magazineId);
    }

    @PutMapping("/magazines/{magazineId}")
    public Magazine updateMagazine(@PathVariable("magazineId") int magazineId, @RequestBody Magazine magazine) {
        return magazineJpaService.updateMagazine(magazineId, magazine);
    }

    @DeleteMapping("/magazines/{magazineId}")
    public void deleteMagazin(@PathVariable("magazineId") int magazineId) {
        magazineJpaService.deleteMagazine(magazineId);
    }

    @GetMapping("/magazines/{magazineId}/writers")
    public List<Writer> getMagazineWriters(@PathVariable("magazineId") int magazineId) {
        return magazineJpaService.getMagazineWriters(magazineId);
    }
}