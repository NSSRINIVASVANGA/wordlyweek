package com.example.wordlyweek.repository;

import java.util.*;
import com.example.wordlyweek.model.*;

public interface MagazineRepository {
    ArrayList<Magazine> getMagazines();

    Magazine addMagazine(Magazine magazine);

    Magazine getMagazineById(int magazineId);

    Magazine updateMagazine(int magazineId, Magazine magazine);

    void deleteMagazine(int magazine);

    List<Writer> getMagazineWriters(int magazineId);
}