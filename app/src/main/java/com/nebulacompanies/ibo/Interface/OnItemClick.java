package com.nebulacompanies.ibo.Interface;

import java.util.List;
import java.util.Set;

public interface OnItemClick {
    void onClick(Integer parentposition, Integer childposition, List<Integer> elementsids);

    void onSubmit();

    void updateData(Integer nochangepos, Set<Integer> selfinal,List<Integer> elementsids);

    void updateMismatch();
}