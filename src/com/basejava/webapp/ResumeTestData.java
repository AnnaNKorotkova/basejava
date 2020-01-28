package com.basejava.webapp;

import com.basejava.webapp.model.*;
import com.basejava.webapp.util.DateUtil;

import java.time.Month;
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

                    listExp.add(new TimeLine("Alcatel", "", new ArrayList<>(Collections.singletonList(new TimeLine.Item(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY),
                            "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")))));
                    listExp.add(new TimeLine("Siemens AG", "",new ArrayList<> (Collections.singletonList(new TimeLine.Item(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY),
                            "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")))));
                    listExp.add(new TimeLine("Enkata","", new ArrayList<> (Collections.singletonList(new TimeLine.Item(DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JULY),
                            "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")))));
                    resumeSection.put(tp, new TimeLineSection(listExp));
                    break;
                case EDUCATION:
                    List<TimeLine> listEdu = new ArrayList<>();
                    
                    listEdu.add(new TimeLine("Заочная физико-техническая школа при МФТИ","", new ArrayList<>(Collections.singletonList(new TimeLine.Item(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE), "Закончил с отличием", null)))));
                    listEdu.add(new TimeLine("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "",
                            new ArrayList<>(Arrays.asList(new TimeLine.Item(DateUtil.of(1987,Month.SEPTEMBER), DateUtil.of(1993,Month.JULY), "Инженер (программист Fortran, C)", null),
                                    new TimeLine.Item(DateUtil.of(1993,Month.SEPTEMBER), DateUtil.of(1996,Month.JULY), "Аспирантура (программист С, С++)", null)))));
                    resumeSection.put(tp, new TimeLineSection(listEdu));
                    break;
            }
        }
        Resume resume = new Resume(fullName, contactSection, resumeSection);
        System.out.println(resume.toString());
    }
}