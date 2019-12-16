package com.basejava;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] arrResume = new Resume[10000];
    private int uuid;

    public int size(Resume[] arrResume) {
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            while (null != arrResume[i]) {
                count++;
            }
        }
        return count;
    }

    public String get(Resume resume) {
        if (resume != null) {
            resume.getName();
            resume.getBirthday();
            resume.getEducation();
            resume.getJobs();
            resume.getAbility();
            resume.getContactPhone();
            resume.getContactEmail();
        }
        return resume.toString();
    }

    public void delete(int index) {
        for (int k = index; k < size(arrResume); k++) {
            arrResume[k] = arrResume[k + 1];
        }
    }

    public void clear(Resume[] arrResume) {
        Arrays.fill(arrResume, null);
    }

    public void getAll(Resume[] arrResume) {
        for (int i = 0; i < size(arrResume); i++) {
            get(arrResume[i]);
            System.out.println(arrResume[i]);
        }
    }

    public void save(Resume[] arrResume, Resume resume) {
        resume = arrResume[size(arrResume)];
    }
}