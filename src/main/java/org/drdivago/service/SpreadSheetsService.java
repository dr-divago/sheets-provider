package org.drdivago.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SpreadSheetsService {
    private static final String APPLICATION_NAME = "Google Sheets API Java Reader";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

    private Optional<Credential> getCredentials() throws IOException, GeneralSecurityException {

        try (InputStream inputStream = SpreadSheetsService.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (inputStream != null) {
                GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                        GsonFactory.getDefaultInstance(),
                        new InputStreamReader(inputStream)
                );
                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        clientSecrets,
                        Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
                )
                        .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                        .setAccessType("offline")
                        .build();

                return Optional.of(new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user"));
            }
        }

        return Optional.empty();

    }

    public List<List<String>> getData(String spreadSheetId, String range) throws GeneralSecurityException, IOException {
        Optional<Credential> credential = getCredentials();
        return credential.map(cred -> {
            try {
                Sheets sheetsService = new Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        cred
                )
                        .setApplicationName(APPLICATION_NAME)
                        .build();
                ValueRange response = sheetsService.spreadsheets().values()
                        .get(spreadSheetId, range)
                        .execute();

                return response.getValues().stream()
                        .map(row -> row.stream()
                                .map(Object::toString)
                                .collect(Collectors.toList()))
                        .toList();
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }).orElse(Collections.emptyList());
    }
}
