import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.omni.domain.model.ConversionResult;
import sh.omni.domain.model.FileContent;
import sh.omni.domain.services.ConverterService;
import sh.omni.strategies.StrategyRegistry;
import sh.omni.strategies.TxtToPdfStrategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterServiceTest {
    private ConverterService converter;
    private StrategyRegistry strategyRegistry;

    @BeforeEach
    void setUp() {
        strategyRegistry = new StrategyRegistry();
        strategyRegistry.registerStrategy(new TxtToPdfStrategy());
        converter = new ConverterService(strategyRegistry);
    }

    @Test
    void testBasicConversion() {
        String asciiArt = """
                   $$$$$$$   $$$$$$$
                $$$$....$$   $$....$$$$
             $$.........$$   $$.........$$
            $$......$$$$$.   .$$$$$......$$
            $......$$             $$......$
            $$$$$$$$$             $$$$$$$$$
            
            $$$$$$$$$             $$$$$$$$$
            $......$$             $$......$
            $$......$$$$$.   .$$$$$......$$
             $$.........$$   $$.........$$
                $$$$....$$   $$....$$$$
                   $$$$$$$   $$$$$$$
            """;

        FileContent inputContent = new FileContent(
                "logo",
                asciiArt.getBytes(),
                "txt"
        );

        ConversionResult result = converter.convert(inputContent, "pdf");
        assertTrue(result.success());
        assertNotNull(result.convertedContent());
    }
}