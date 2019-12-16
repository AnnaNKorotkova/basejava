package com.basejava;

import java.util.Random;
import java.util.Scanner;

public class Resume {

    private String name;
    private String birthday;
    private String education;
    private String jobs;
    private String ability;
    private String contactPhone;
    private String contactEmail;
    private long uuid;
    Scanner scan = new Scanner(System.in);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid() {
        final Random random = new Random(System.currentTimeMillis());
        this.uuid = random.nextLong();
    }

    public void enterResume() {
        System.out.println("Заполнение резюме");
        System.out.print("Введите имя: ");
        setName(scan.next());
        System.out.print("Дата рождения: ");
        setBirthday(scan.next());
        System.out.print("Образование: ");
        setEducation(scan.next());
        System.out.print("Опыт работы: ");
        setJobs(scan.next());
        System.out.print("Личные качества: ");
        setAbility(scan.next());
        System.out.print("Контактный телефон: ");
        setContactPhone(scan.next());
        System.out.print("E-mail: ");
        setContactEmail(scan.next());
        setUuid();
    }

    @Override
    public String toString() {
        return "Resume{" +
                "Имя = '" + name + '\'' +
                ", Дата рождения = " + birthday +
                ", Образование = '" + education + '\'' +
                ", Опыт работы = '" + jobs + '\'' +
                ", Личные качества = '" + ability + '\'' +
                ", Телефон ='" + contactPhone + '\'' +
                ", E-mail = '" + contactEmail + '\'' +
                '}';
    }
}