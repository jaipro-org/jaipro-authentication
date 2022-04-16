/*
package com.bindord.eureka.auth.configuration;

import com.bindord.eureka.auth.domain.base.BaseDomain;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AbstractRelationalEventListener;
import org.springframework.data.relational.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Configuration
public class BeforeConvertListener extends AbstractRelationalEventListener<BaseDomain> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseDomain> base) {
        BaseDomain bsDom = (BaseDomain) base.getSource();
        if (Objects.isNull(bsDom.getCreationDate())) {
            bsDom.setCreationDate(LocalDateTime.now());
        }

        if (Objects.isNull(bsDom.getCreatedBy())) {
            bsDom.setCreatedBy(UUID.randomUUID().toString());
        }

        bsDom.setModifiedBy(null);
        bsDom.setModifiedDate(null);
    }

    @Override
    protected void onBeforeSave(BeforeSaveEvent<BaseDomain> event) {
        BaseDomain bsDom = (BaseDomain) event.getSource();
        if (Objects.isNull(bsDom.getCreationDate())) {
            bsDom.setCreationDate(LocalDateTime.now());
        }
        super.onBeforeSave(event);
    }
}
*/
