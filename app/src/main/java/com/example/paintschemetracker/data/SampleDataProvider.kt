package com.example.paintschemetracker.data

import java.util.*

class SampleDataProvider {
//This is the sample data provider, here, 3 data objedcts that are examples of the format of the data, are generated and added to the database via the item menu
    companion object{
        private val sampleMiniature1 = "Blood Angels paint scheme"
        private val sampleMiniature2 = "Custodian Guard"
        private val sampleMiniature3 = "Ulthwe Eldar"

        private fun getDate(diff: Long): Date{
            return Date(Date().time+diff)
        }
//this is an array list that contains 3 data objects that are each a paint scheme object
        fun getMiniatures() = arrayListOf(
            MiniatureEntity(sampleMiniature1,
                "Black primer, Vallejo fire red, Citadel Troll Slayer Orange, Vallejo metal colour gold, vallejo metal colour silver, vallejo matte black, vallejo pure white, P3 ordic olive, pro acryl olive flesh",
                "Base coating, layering, edge highlighting, glazing",
                 "Step 1: Prime model with black primer" +
                        "Step 2: Base coat model using vallejo fire red" +
                        "Step 3: Place vallejo matte black in the arm and leg joints, as well as one shoulder pad and the weapon casing" +
                        "Step 4: Place vallejo model colour gold in the chest and accent pieces" +
                        "Step 5: Place Pro acryl olive flesh on the face area in thin glazes" +
                        "Step 6: Highlight flesh tone with vallejo pure white mixed in a 50/50 ratio with olive flesh" +
                        "Step 7: Highlight troll slayer orange onto the sharpest corners and edges of the red armour" +
                        "Step 8 : Paint the purity seals with ordic olive",
                "Blood angel intercessor"),
            MiniatureEntity(sampleMiniature2,   "Black primer, Vallejo polished gold, vallejo metal colour gold, vallejo oxford blue, vallejo glacier blue, citadel fenrisian grey, citadel seraphim sepia, scalecolour brown leather, p3 rucksack tan" +
                                                                ", vallejo metal colour silver, vallejo matte black, vallejo pure white",
                "Base coating, layering, edge highlighting, glazing, weathering, recess washing",
                "Step 1: Prime model with black primer" +
                        "Step 2: Base coat model using vallejo polished gold" +
                        "Step 3: Place vallejo matte black in the arm and leg joints, and the weapon casing" +
                        "Step 4: place vallejo oxford blue on the blade and glaze over once dry with citadel fenrisian grey" +
                        "Step 5: basecoat any leather areas with scalecolour brown leather and scratch with rucksack tan to create a weathered look" +
                        "Step 6: recess shade the gold with seraphim sepia, then highlight the gold with metal colour gold" +
                        "Step 7: Higlight the blade with vallejo glacier blue and with pure white "+
                        "Step 8 : Paint the eye lenses with a blend of oxford blue and glacier blue",
                "Custodian Guard"),
            MiniatureEntity(sampleMiniature3,   "Black primer, vallejo metal colour copper, citadel fenrisian grey, scalecolour brown leather, p3 rucksack tan, , vallejo matte black, vallejo pure white, scalecolour black leather, citadel zandri dust, vallejo pale sand, citadel khorne red, kimera red",
                "Base coating, layering, edge highlighting, weathering, volumetric highlighting",
                "Step 1: Prime model with black primer" +
                        "Step 2: Base coat model using vallejo matte black" +
                        "Step 3: paint vallejo brown leather on any areas that are leather, and highlight and scrath them with rucksack tan" +
                        "Step 4: wet blend black leather, and fenrisian grey on the edge of the black armour" +
                        "Step 5: paint eyes and gems with vollumetric highlights, first with black, then khorne red, then kimera red, then a white dot in the top corner to show a reflection" +
                        "Step 6: paint the shoulder pad and head first with zandri dust to create a base followed by pale sand, finally highlighted with pure white" +
                        "Step 7: paint staff with a silver blade and glaze red over it "+
                        "Step 8 : paint any accents with vallejo metal colour copper",
                "Eldar Farseer"),
        )
    }
}