package com.example.mannas.book_e.App_Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mannas
 */
public class Utility {
    public static String separateWithComa(ArrayList<String> ls){
        String str = "";
        for(String s : ls){
            str += s + ", ";
        }
        if(str!="")
            return str.substring(0,str.length()-2);
        return "";
    }
    public static String reTag(String tag){
        tag = tag.toLowerCase();
        tag = tag.replace(' ','_');
        return tag;
    }

    public static final String ToReadURL = "https://openlibrary.org/read.json";

    public static List<String> Subjects = new ArrayList<>(
            Arrays.asList(
                    "love", "hate", "will",
                    "accessible_book",
                    "psychology",
                    "methods",
                    "behavior_therapy",
                    "social_aspects",
                    "intelligence",
                    "interpersonal_relations",
                    "overdrive",
                    "nonfiction",
                    "protected_daisy",
                    "large_type_books",
                    "intellect",
                    "emotion",
                    "in_library",
                    "personality",
                    "psychoanalysis",
                    "development"
                    ));

//    public static final String[] Subjects = {
//            "will",
//            "accessible_book",
//            "psychology",
//            "methods",
//            "rational",
//            "case studies",
//            "behavior_therapy",
//            "social_aspects",
//            "intelligence",
//            "interpersonal_relations",
//            "overdrive",
//            "nonfiction",
//            "protected_daisy",
//            "large_type_books",
//            "intellect",
//            "emotion",
//            "in_library",
//            "personality",
//            "love",
//            "hate",
//            "psychoanalysis",
//            "development",
//            "desarrollo",
//            "child_behavior",
//            "parent_child_relations",
//            "psicología",
//            "child",
//            "infant",
//            "child_development",
//            "child psychology",
//            "popular_works",
//            "poor",
//            "sociology",
//            "bible",
//            "evidences_authority",
//            "christian_sociology",
//            "economics",
//            "presbyterian_church",
//            "theology",
//            "apologetics",
//            "collected_works",
//            "social_ethics",
//            "labor_and_laboring_classes",
//            "social_conditions",
//            "commentaries",
//            "population",
//            "history",
//            "early_worksto_1850",
//            "ethics",
//            "problems_exercises",
//            "man_woman_relationships",
//            "readers",
//            "latin_love_poetry",
//            "poetry",
//            "translations_into_spanish",
//            "latin",
//            "latin_language",
//            "metamorphosis",
//            "cosmetics",
//            "classical_mythology",
//            "mythology",
//            "sex_(psychology)",
//            "sexualit_(psychologie)",
//            "homosexualité",
//            "sexual_perversion",
//            "criminal_psychology",
//            "sex_disorders",
//            "sexual_deviation",
//            "psychology_pathological",
//            "sexual_behavior",
//            "paraphilias",
//            "newbery_medal",
//            "juvenile_fiction",
//            "space_and_time",
//            "fiction",
//            "science_fiction",
//            "friendship_in_adolescence",
//            "time_travel",
//            "liefde",
//            "amour",
//            "padre_e_hijo",
//            "braille_books",
//            "fathers_and_sons",
//            "hora_de_acostarse",
//            "fiction",
//            "ficción_juvenil",
//            "bedtime",
//            "conejos",
//            "high_schools",
//            "pregnancy",
//            "united_states",
//            "orgasm",
//            "woman",
//            "brigands_and_robbers",
//            "fiction",
//            "cristianismo",
//            "religious_aspects"
// };

}
