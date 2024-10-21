package com.example.sugarboat.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.sugarboat.model.Interesting;

public interface ParseInterrestings extends Serializable {
    void sendChecked(String interestings);
}
