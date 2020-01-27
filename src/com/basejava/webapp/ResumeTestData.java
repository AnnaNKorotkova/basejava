package com.basejava.webapp;

import com.basejava.webapp.model.*;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResumeTestData {

    public static void main(String[] args) {

        String fullName = "Григорий Кислин";

        Map<Contact, String> contactSection = new EnumMap<>(Contact.class);

        for (Contact c : Contact.values()) {
            switch (c) {
                case PHONE:
                    contactSection.put(c, " 7 911 111 11 11 ");
                    break;
                case SKYPE:
                    contactSection.put(c, "wer");
                    break;
                case EMAIL:
                    contactSection.put(c, "qqqq@mail.ru");
                    break;
                case SOCIAL_NETS:
                    contactSection.put(c, "Vk/123");
                    break;
            }
        }

        Map<TypeSection, AbstractSection> resumeSection = new EnumMap<>(TypeSection.class);

        for (TypeSection tp : TypeSection.values()) {

            switch (tp) {
                case PERSONAL:
                    resumeSection.put(tp, new TextSection("Аналитический склад ума, сильная логика, " +
                            "креативность, инициативность. Пурист кода и архитектуры."));
                    break;
                case OBJECTIVE:
                    resumeSection.put(tp, new TextSection("Ведущий стажировок и корпоративного " +
                            "обучения по Java Web и Enterprise технологиям"));
                    break;
                case ACHIEVEMENT:
                    List<String> achievements = Arrays.asList(
                            "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                            "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                            "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                            "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                            "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                            "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
                    );
                    resumeSection.put(tp, new ListSection(achievements));
                    break;
                case QUALIFICATIONS:
                    List<String> qualification = Arrays.asList(
                            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                            "MySQL, SQLite, MS SQL, HSQLDB",
                            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                            "Python: Django.",
                            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                            "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                            "Родной русский, английский \"upper intermediate\""
                    );
                    resumeSection.put(tp, new ListSection(qualification));
                    break;
                case EXPERIENCE:
                    List<TimeLine> listExp = new ArrayList<>();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/YYYY");
                    listExp.add(new TimeLine("Alcatel", "",LocalDate.parse("9/1997",dtf) ,"01/2005", "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
                    listExp.add(new TimeLine("Siemens AG", "","01/2005", "02/2007", "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
                    listExp.add(new TimeLine("Enkata","", "03/2007", "06/2008", "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
                    resumeSection.put(tp, new TimeLineSection(listExp));
                    break;
                case EDUCATION:
                    List<TimeLine> listEdu = new ArrayList<>();
                    listEdu.add(new TimeLine("Заочная физико-техническая школа при МФТИ", "","09/1984", "06/1987", "Закончил с отличием", null));
                    listEdu.add(new TimeLine("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "","09/1987", "07/1993", "Инженер (программист Fortran, C)", null));
                    listEdu.add(new TimeLine("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "","09/1993", "07/1996", "Аспирантура (программист С, С++)", null));
                    listEdu.add(new TimeLine("Alcatel", "","09/1997", "03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)", null));
                    resumeSection.put(tp, new TimeLineSection(listEdu));
                    break;

            }
        }

        Resume resume = new Resume(fullName, contactSection, resumeSection);
        System.out.println(resume.toString());

    }
}

