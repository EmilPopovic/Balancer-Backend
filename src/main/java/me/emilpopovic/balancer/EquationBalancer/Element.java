package me.emilpopovic.balancer.EquationBalancer;

import java.util.LinkedList;
import java.util.List;

public enum Element {
    H ("Hydrogen", "H"),
    HE("Helium", "He"),
    LI("Lithium", "Li"),
    BE("Beryllium", "Be"),
    B ("Boron", "B"),
    C ("Carbon", "C"),
    N ("Nitrogen", "N"),
    O ("Oxygen", "O"),
    F ("Fluorine", "F"),
    NE("Neon", "Ne"),
    NA("Sodium", "Na"),
    MG("Magnesium", "Mg"),
    AL("Aluminium", "Al"),
    SI("Silicon", "Si"),
    P ("Phosphorus", "P"),
    S ("Sulfur", "S"),
    CL("Chlorine", "Cl"),
    AR("Argon", "Ar"),
    K ("Potassium", "K"),
    CA("Calcium", "Ca"),
    SC("Scandium", "Sc"),
    TI("Titanium", "Ti"),
    V ("Vanadium", "V"),
    CR("Chromium", "Cr"),
    MN("Manganese", "Mn"),
    FE("Iron", "Fe"),
    CO("Cobalt", "Co"),
    NI("Nickel", "Ni"),
    CU("Copper", "Cu"),
    ZN("Zinc", "Zn"),
    GA("Gallium", "Ga"),
    GE("Germanium", "Ge"),
    AS("Arsenic", "As"),
    SE("Selenium", "Se"),
    BR("Bromine", "Br"),
    KR("Krypton", "Kr"),
    RB("Rubidium", "Rb"),
    SR("Strontium", "Sr"),
    Y ("Yttrium", "Y"),
    ZR("Zirconium", "Zr"),
    NB("Niobium", "Nb"),
    MO("Molybdenum", "Mo"),
    TC("Technetium", "Tc"),
    RU("Ruthenium", "Ru"),
    RH("Rhodium", "Rh"),
    PD("Palladium", "Pd"),
    AG("Silver", "Ag"),
    CD("Cadmium", "Cd"),
    IN("Indium", "In"),
    SN("Tin", "Sn"),
    SB("Antimony", "Sb"),
    TE("Tellurium", "Te"),
    I ("Iodine", "I"),
    XE("Xenon", "Xe"),
    CS("Caesium", "Cs"),
    BA("Barium", "Ba"),
    LA("Lanthanum", "La"),
    CE("Cerium", "Ce"),
    PR("Praseodymium", "Pr"),
    ND("Neodymium", "Nd"),
    PM("Promethium", "Pm"),
    SM("Samarium", "Sm"),
    EU("Europium", "Eu"),
    GD("Gadolinium", "Gd"),
    TB("Terbium", "Tb"),
    DY("Dysprosium", "Dy"),
    HO("Holmium", "Ho"),
    ER("Erbium", "Er"),
    TM("Thulium", "Tm"),
    YB("Ytterbium", "Yb"),
    LU("Lutetium", "Lu"),
    HF("Hafnium", "Hf"),
    TA("Tantalum", "Ta"),
    W ("Tungsten", "W"),
    RE("Rhenium", "Re"),
    OS("Osmium", "Os"),
    IR("Iridium", "Ir"),
    PT("Platinum", "Pt"),
    AU("Gold", "Au"),
    HG("Mercury", "Hg"),
    TL("Thallium", "Tl"),
    PB("Lead", "Pb"),
    BI("Bismuth", "Bi"),
    PO("Polonium", "Po"),
    AT("Astatine", "At"),
    RN("Radon", "Rn"),
    FR("Francium", "Fr"),
    RA("Radium", "Ra"),
    AC("Actinium", "Ac"),
    TH("Thorium", "Th"),
    PA("Protactinium", "Pa"),
    U ("Uranium", "U"),
    NP("Neptunium", "Np"),
    PU("Plutonium", "Pu"),
    AM("Americium", "Am"),
    CM("Curium", "Cm"),
    BK("Berkelium", "Bk"),
    CF("Californium", "Cf"),
    ES("Einsteinium", "Es"),
    FM("Fermium", "Fm"),
    MD("Mendelevium", "Md"),
    NO("Nobelium", "No"),
    LR("Lawrencium", "Lr"),
    RF("Rutherfordium", "Rf"),
    DB("Dubnium", "Db"),
    SG("Seaborgium", "Sg"),
    BH("Bohrium", "Bh"),
    HS("Hassium", "Hs"),
    MT("Meitnerium", "Mt"),
    DS("Darmstadtium", "Ds"),
    RG("Roentgenium", "Rg"),
    CN("Copernicium", "Cn"),
    NH("Nihonium", "Nh"),
    FL("Flerovium", "Fl"),
    MC("Moscovium", "Mc"),
    LV("Livermorium", "Lv"),
    TS("Tennessine", "Ts"),
    OG("Oganesson", "Og");

    public final String name;
    public final String symbol;

    Element(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    private static List<Element> getWithSymbolLength(int n) {
        List<Element> elements = new LinkedList<>();
        for (Element e: values()) {
            if (e.symbol.length() == n) {
                elements.add(e);
            }
        }
        return elements;
    }

    public static List<Element> getOneLetter() {
        return getWithSymbolLength(1);
    }

    public static List<Element> getTwoLetter() {
        return getWithSymbolLength(2);
    }

    public static Element withSymbol(String symbol) {
        for (Element e: values()) {
            if (e.symbol.equals(symbol)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No such symbol: " + symbol);
    }
}
