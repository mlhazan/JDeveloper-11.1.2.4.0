package com.packt.jdeveloper.cookbook.shared.extensions;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.adf.share.logging.ADFLogger;

public class ExtApplicationModuleImpl extends ApplicationModuleImpl {
    private static final ADFLogger LOGGER = ADFLogger.createADFLogger(ExtApplicationModuleImpl.class);

    public ExtApplicationModuleImpl() {
        super();
        LOGGER.log(ADFLogger.TRACE, "ExtApplicationModuleImpl was constructed");
    }

}
