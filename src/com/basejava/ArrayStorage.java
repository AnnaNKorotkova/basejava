package com.basejava;

import java.util.Arrays;

public class ArrayStorage {

    private Resume[] arrResume;
    private int size;
    private int lastUuid;

    public ArrayStorage(Resume[] arrResume) {
        this.arrResume = arrResume;
    }

    public void get(Resume resume) {
        if (resume == null) {
            System.out.println("Запршенного резюме не существует.");
        } else System.out.println(resume.toString());
    }

    public void getAll(Resume[] arrResume) {
        for (int i = 0; i < size; i++) {
            get(arrResume[i]);
        }
    }

    public void save(Resume[] arrResume) {
        Resume resume = new Resume();
        resume.enterResume();
        lastUuid += 1;
        resume.setUuid(lastUuid);
        arrResume[size] = resume;
        size += 1;
        System.out.println("Резюме  id=" + lastUuid + ", создано.");
    }

    public void delete(int index) {
        if (size > index) {
            int id = arrResume[index].getUuid();
            for (int k = index; k < size - 1; k++) {
                arrResume[k] = arrResume[k + 1];
            }
            arrResume[size - 1] = null;
            size -= 1;
            System.out.println("Резюме id=" + id + ", с индексом: " + index + ", удалено.");
        } else {
            System.out.println("Невозможно удалить резюме с индексом: " + index + ", такого резюме не существует.");
        }
    }

    public void clear(Resume[] arrResume) {
        Arrays.fill(arrResume, null);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public String findIndexByUuid(int uuid) {
        for (int i = 0; i < size; i++) {
            if (arrResume[i].getUuid() == uuid) {
                return String.valueOf(i);
            }
        }
        return "Резюме с таким id:" + uuid + "не найдено";
    }
}