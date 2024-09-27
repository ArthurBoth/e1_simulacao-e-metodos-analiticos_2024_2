package auxiliaries;

public abstract class Configs {

    // Confgurações de simulação
    public static final int ITERATIONS  = 100000;

    // Configurações do gerador de números pseudoaleatórios
    public static final double SEED         = 3.1415926535;     
    public static final int MULTIPLIER      = 157907;            
    public static final double INCREMENT    = 14423.2563217511;  
    public static final double MODULE       = 16651.8647227172;  

    // Constantes para impressão na tela
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private Configs() {}
}