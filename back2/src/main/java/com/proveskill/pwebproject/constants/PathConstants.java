package com.proveskill.pwebproject.constants;

public class PathConstants {
    public static final String BASE_PATH = "/api";
    public static final String AUTH = BASE_PATH + "/auth";

    public static final String LOGIN = AUTH + "/login";
    public static final String REGISTER = AUTH + "/register";
    public static final String FORGOT_PASSWORD = AUTH + "/forgot-password";

    public static final String USERS = "/users";
    public static final String STUDENT = "/student";
    public static final String QUESTIONS = "/questions";
    public static final String EXAMS = "/exams";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
