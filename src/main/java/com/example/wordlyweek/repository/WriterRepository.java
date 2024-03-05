package com.example.wordlyweek.repository;

import java.util.*;
import com.example.wordlyweek.model.*;

public interface WriterRepository {
    ArrayList<Writer> getWriters();

    Writer addWriter(Writer writer);

    Writer getWriterById(int writerId);

    Writer updateWriter(int writerId, Writer writer);

    void deleteWriter(int WriterId);

    List<Magazine> getWriterMagazines(int writerId);
}