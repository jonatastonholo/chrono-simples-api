package dev.tonholo.chronosimplesapi.architecture;

import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayersDefinition {
    static final String DOMAIN_LAYER = "Domain";
    static final String SERVICE_LAYER = "Service";
    static final String WEB_LAYER = "Web";
    static final String DTO_LAYER = "Dto";
    static final String REPOSITORY_LAYER = "Repository";
    static final String TRANSFORMATION_LAYER = "Transformation";

    static final Architectures.LayeredArchitecture PROJECT_LAYERS =
            layeredArchitecture()
                    .layer(DOMAIN_LAYER).definedBy("..domain..")
                    .layer(SERVICE_LAYER).definedBy("..service..")
                    .layer(WEB_LAYER).definedBy("..web..")
                    .layer(DTO_LAYER).definedBy("..dto..", "..event..", "..entity..", "..model..")
                    .layer(REPOSITORY_LAYER).definedBy("..repository..")
                    .layer(TRANSFORMATION_LAYER).definedBy("..transformer..", "..mapper..", "..converter..")
            ;
}
