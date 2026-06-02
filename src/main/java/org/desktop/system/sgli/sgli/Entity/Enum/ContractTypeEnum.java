package org.desktop.system.sgli.sgli.Entity.Enum;

public enum ContractTypeEnum {
    CAUCAO("Caução"),
    FIADOR("Fiador"),
    NO_INFORM("Sem Informação");

    private final String label;

    ContractTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return label;
    }
}
