package dev.tonholo.chronosimplesapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.tonholo.chronosimplesapi.architecture.LayersDefinition.*;

class LayerViolationRulesTest {
    private static final String PROJECT_PACKAGE = "dev.tonholo.chronosimplesapi";

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(new com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests())
            .importPackages(PROJECT_PACKAGE);

    @Test
    @DisplayName("The Repository layer can only be accessed through the Service layer")
    void layerViolationRulesTest01() {
        PROJECT_LAYERS
                .whereLayer(REPOSITORY_LAYER)
                .mayOnlyBeAccessedByLayers(SERVICE_LAYER)
                .check(importedClasses);
    }

    @Test
    @DisplayName("The Service layer can only be accessed through the Web and External layer")
    void layerViolationRulesTest02() {

        PROJECT_LAYERS
                .whereLayer(SERVICE_LAYER)
                .mayOnlyBeAccessedByLayers(WEB_LAYER)
                .check(importedClasses);
    }

    @Test
    @DisplayName("The Web layer must not be accessed through any other layer")
    void layerViolationRulesTest03() {

        PROJECT_LAYERS
                .whereLayer(WEB_LAYER)
                .mayNotBeAccessedByAnyLayer()
                .check(importedClasses);
    }

    @Test
    @DisplayName("The Domain layer can only be accessed through the Service, Repository, DTOs and Transformers layer")
    void layerViolationRulesTest04() {

        PROJECT_LAYERS
                .whereLayer(DOMAIN_LAYER)
                .mayOnlyBeAccessedByLayers(SERVICE_LAYER, REPOSITORY_LAYER, DTO_LAYER, TRANSFORMATION_LAYER)
                .check(importedClasses);
    }
}
