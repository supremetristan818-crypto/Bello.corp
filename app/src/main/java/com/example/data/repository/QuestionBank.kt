package com.example.data.repository

import com.example.data.model.Question

object QuestionBank {

    val SUBJECTS = listOf(
        "Mathematics",
        "Physics",
        "Chemistry",
        "Biology",
        "English",
        "Social Studies",
        "ICT / Computer Science"
    )

    val ALL_QUESTIONS: List<Question> = listOf(
        // ================= MATHEMATICS (10 Questions) =================
        Question(
            id = "math_01",
            subject = "Mathematics",
            topic = "Algebra",
            difficulty = "Easy",
            question = "Solve for x in the equation: 3x - 7 = 14.",
            options = listOf("x = 5", "x = 7", "x = 21", "x = 9"),
            correctIndex = 1,
            explanation = "Add 7 to both sides: 3x = 21. Then divide by 3: x = 7.",
            xp = 50
        ),
        Question(
            id = "math_02",
            subject = "Mathematics",
            topic = "Quadratic equations",
            difficulty = "Medium",
            question = "What are the roots of the quadratic equation x² - 5x + 6 = 0?",
            options = listOf("x = 2 and x = 3", "x = -2 and x = -3", "x = 1 and x = 6", "x = -1 and x = 5"),
            correctIndex = 0,
            explanation = "Factoring (x - 2)(x - 3) = 0 gives roots x = 2 and x = 3.",
            xp = 75
        ),
        Question(
            id = "math_03",
            subject = "Mathematics",
            topic = "Sequences",
            difficulty = "Medium",
            question = "In an arithmetic progression: 4, 9, 14, 19..., what is the 15th term?",
            options = listOf("70", "74", "79", "84"),
            correctIndex = 1,
            explanation = "Formula: an = a + (n - 1)d. Here a = 4, d = 5, n = 15. So 4 + (14 * 5) = 4 + 70 = 74.",
            xp = 75
        ),
        Question(
            id = "math_04",
            subject = "Mathematics",
            topic = "Geometry",
            difficulty = "Easy",
            question = "What is the sum of interior angles in a regular hexagon?",
            options = listOf("360°", "540°", "720°", "900°"),
            correctIndex = 2,
            explanation = "Formula: (n - 2) * 180°. For n = 6: (6 - 2) * 180° = 4 * 180° = 720°.",
            xp = 50
        ),
        Question(
            id = "math_05",
            subject = "Mathematics",
            topic = "Trigonometry",
            difficulty = "Hard",
            question = "If sin(θ) = 3/5 in the first quadrant, what is the value of tan(θ)?",
            options = listOf("3/4", "4/3", "4/5", "5/4"),
            correctIndex = 0,
            explanation = "In a 3-4-5 right triangle, opposite = 3, adjacent = 4. tan(θ) = opposite/adjacent = 3/4.",
            xp = 100
        ),
        Question(
            id = "math_06",
            subject = "Mathematics",
            topic = "Statistics",
            difficulty = "Medium",
            question = "What is the median of the dataset: 8, 3, 14, 9, 21, 6, 12?",
            options = listOf("8", "9", "12", "11"),
            correctIndex = 1,
            explanation = "Sorted: 3, 6, 8, 9, 12, 14, 21. The middle value is 9.",
            xp = 75
        ),
        Question(
            id = "math_07",
            subject = "Mathematics",
            topic = "Algebra",
            difficulty = "Hard",
            question = "If log₁₀(x) + log₁₀(x - 3) = 1, what is the positive real value of x?",
            options = listOf("x = 2", "x = 4", "x = 5", "x = 10"),
            correctIndex = 2,
            explanation = "log₁₀(x(x - 3)) = 1 implies x² - 3x - 10 = 0 -> (x - 5)(x + 2) = 0. Since x > 3, x = 5.",
            xp = 100
        ),
        Question(
            id = "math_08",
            subject = "Mathematics",
            topic = "Geometry",
            difficulty = "Medium",
            question = "What is the volume of a sphere with radius 3 cm? (Use V = 4/3 π r³)",
            options = listOf("12π cm³", "27π cm³", "36π cm³", "48π cm³"),
            correctIndex = 2,
            explanation = "V = 4/3 * π * 3³ = 4/3 * π * 27 = 36π cm³.",
            xp = 75
        ),
        Question(
            id = "math_09",
            subject = "Mathematics",
            topic = "Trigonometry",
            difficulty = "Extreme",
            question = "What is the exact value of cos(2x) if cos(x) = 1/3?",
            options = listOf("-7/9", "-5/9", "1/9", "7/9"),
            correctIndex = 0,
            explanation = "Identity: cos(2x) = 2cos²(x) - 1 = 2(1/9) - 1 = 2/9 - 1 = -7/9.",
            xp = 150
        ),
        Question(
            id = "math_10",
            subject = "Mathematics",
            topic = "Statistics",
            difficulty = "Easy",
            question = "A fair six-sided die is rolled. What is the probability of rolling a prime number?",
            options = listOf("1/6", "1/3", "1/2", "2/3"),
            correctIndex = 2,
            explanation = "Prime numbers on a die are 2, 3, 5 (3 outcomes out of 6). Probability = 3/6 = 1/2.",
            xp = 50
        ),

        // ================= PHYSICS (10 Questions) =================
        Question(
            id = "phys_01",
            subject = "Physics",
            topic = "Forces",
            difficulty = "Easy",
            question = "Which physical quantity is measured in Newtons (N)?",
            options = listOf("Energy", "Force", "Power", "Pressure"),
            correctIndex = 1,
            explanation = "Force is measured in Newtons (kg·m/s²).",
            xp = 50
        ),
        Question(
            id = "phys_02",
            subject = "Physics",
            topic = "Motion",
            difficulty = "Medium",
            question = "A sports car accelerates from rest at 4 m/s² for 5 seconds. How far has it traveled?",
            options = listOf("20 m", "40 m", "50 m", "100 m"),
            correctIndex = 2,
            explanation = "Using s = ut + 0.5at² with u = 0: s = 0.5 * 4 * 5² = 2 * 25 = 50 m.",
            xp = 75
        ),
        Question(
            id = "phys_03",
            subject = "Physics",
            topic = "Energy",
            difficulty = "Medium",
            question = "A 2 kg mass is raised 10 meters above the ground. What is its potential energy? (g = 9.8 m/s²)",
            options = listOf("98 J", "196 J", "240 J", "392 J"),
            correctIndex = 1,
            explanation = "Ep = m * g * h = 2 * 9.8 * 10 = 196 Joules.",
            xp = 75
        ),
        Question(
            id = "phys_04",
            subject = "Physics",
            topic = "Waves",
            difficulty = "Easy",
            question = "What type of wave is sound traveling through air?",
            options = listOf("Transverse electromagnetic wave", "Longitudinal mechanical wave", "Surface wave", "Standing polar wave"),
            correctIndex = 1,
            explanation = "Sound in air travels as compression and rarefaction: longitudinal mechanical waves.",
            xp = 50
        ),
        Question(
            id = "phys_05",
            subject = "Physics",
            topic = "Electricity",
            difficulty = "Medium",
            question = "According to Ohm's Law, what current flows through a 24 Ω resistor connected across a 12 V battery?",
            options = listOf("0.5 A", "2.0 A", "12 A", "288 A"),
            correctIndex = 0,
            explanation = "I = V / R = 12 V / 24 Ω = 0.5 Amperes.",
            xp = 75
        ),
        Question(
            id = "phys_06",
            subject = "Physics",
            topic = "Vectors",
            difficulty = "Hard",
            question = "Two perpendicular vectors have magnitudes of 6 units and 8 units. What is the magnitude of their resultant?",
            options = listOf("2 units", "10 units", "14 units", "48 units"),
            correctIndex = 1,
            explanation = "Resultant R = √(6² + 8²) = √(36 + 64) = √100 = 10 units.",
            xp = 100
        ),
        Question(
            id = "phys_07",
            subject = "Physics",
            topic = "Forces",
            difficulty = "Hard",
            question = "Which law states that for every action, there is an equal and opposite reaction?",
            options = listOf("Newton's First Law", "Newton's Second Law", "Newton's Third Law", "Hooke's Law"),
            correctIndex = 2,
            explanation = "Newton's Third Law of Motion states that forces always occur in equal and opposite pairs.",
            xp = 100
        ),
        Question(
            id = "phys_08",
            subject = "Physics",
            topic = "Electricity",
            difficulty = "Hard",
            question = "Three identical 6 Ω resistors are connected in parallel. What is the equivalent resistance?",
            options = listOf("18 Ω", "6 Ω", "3 Ω", "2 Ω"),
            correctIndex = 3,
            explanation = "1/Req = 1/6 + 1/6 + 1/6 = 3/6 = 1/2. Therefore Req = 2 Ω.",
            xp = 100
        ),
        Question(
            id = "phys_09",
            subject = "Physics",
            topic = "Waves",
            difficulty = "Extreme",
            question = "A light ray enters glass (n = 1.5) from air (n = 1.0) at an incident angle of 30°. What is sin(r)?",
            options = listOf("1/3", "1/2", "2/3", "3/4"),
            correctIndex = 0,
            explanation = "Snell's Law: 1.0 * sin(30°) = 1.5 * sin(r) -> 0.5 = 1.5 * sin(r) -> sin(r) = 0.5 / 1.5 = 1/3.",
            xp = 150
        ),
        Question(
            id = "phys_10",
            subject = "Physics",
            topic = "Energy",
            difficulty = "Easy",
            question = "What is the SI unit of electric power?",
            options = listOf("Joule", "Watt", "Volt", "Ampere"),
            correctIndex = 1,
            explanation = "Power is rate of energy transfer, measured in Watts (1 W = 1 J/s).",
            xp = 50
        ),

        // ================= CHEMISTRY (10 Questions) =================
        Question(
            id = "chem_01",
            subject = "Chemistry",
            topic = "Atomic structure",
            difficulty = "Easy",
            question = "How many protons are found in a neutral Carbon-12 atom?",
            options = listOf("4", "6", "12", "14"),
            correctIndex = 1,
            explanation = "Carbon has atomic number 6, which indicates it has 6 protons in its nucleus.",
            xp = 50
        ),
        Question(
            id = "chem_02",
            subject = "Chemistry",
            topic = "Periodic table",
            difficulty = "Medium",
            question = "Which element is the most electronegative on the periodic table?",
            options = listOf("Oxygen", "Chlorine", "Fluorine", "Francium"),
            correctIndex = 2,
            explanation = "Fluorine has the highest electronegativity rating of 3.98 on the Pauling scale.",
            xp = 75
        ),
        Question(
            id = "chem_03",
            subject = "Chemistry",
            topic = "Bonding",
            difficulty = "Medium",
            question = "What type of chemical bonding occurs between Sodium (Na) and Chlorine (Cl)?",
            options = listOf("Covalent bonding", "Ionic bonding", "Metallic bonding", "Hydrogen bonding"),
            correctIndex = 1,
            explanation = "Sodium transfers an electron to chlorine, forming electrostatic ionic bonds between Na⁺ and Cl⁻.",
            xp = 75
        ),
        Question(
            id = "chem_04",
            subject = "Chemistry",
            topic = "Acids and bases",
            difficulty = "Easy",
            question = "What is the pH of a neutral aqueous solution at 25°C?",
            options = listOf("0", "5", "7", "14"),
            correctIndex = 2,
            explanation = "At 25°C, pure water has [H⁺] = 10⁻⁷ M, yielding a neutral pH of 7.",
            xp = 50
        ),
        Question(
            id = "chem_05",
            subject = "Chemistry",
            topic = "Stoichiometry",
            difficulty = "Hard",
            question = "How many moles of gas occupy 22.4 L at standard temperature and pressure (STP)?",
            options = listOf("0.5 mole", "1.0 mole", "2.0 moles", "22.4 moles"),
            correctIndex = 1,
            explanation = "Avogadro's molar volume of any ideal gas at STP is exactly 22.4 liters per mole.",
            xp = 100
        ),
        Question(
            id = "chem_06",
            subject = "Chemistry",
            topic = "Organic chemistry",
            difficulty = "Medium",
            question = "What is the chemical formula of ethanol?",
            options = listOf("CH₄", "CH₃COOH", "C₂H₅OH", "C₃H₈"),
            correctIndex = 2,
            explanation = "Ethanol is an alcohol with 2 carbons: C₂H₅OH.",
            xp = 75
        ),
        Question(
            id = "chem_07",
            subject = "Chemistry",
            topic = "Bonding",
            difficulty = "Hard",
            question = "What is the molecular geometry of a methane (CH₄) molecule?",
            options = listOf("Trigonal planar", "Tetrahedral", "Bent", "Linear"),
            correctIndex = 1,
            explanation = "Carbon forms 4 sp³ hybrid orbital bonds at 109.5° angles, forming a tetrahedron.",
            xp = 100
        ),
        Question(
            id = "chem_08",
            subject = "Chemistry",
            topic = "Acids and bases",
            difficulty = "Hard",
            question = "According to the Brønsted-Lowry definition, what is an acid?",
            options = listOf("A proton acceptor", "A proton donor", "An electron pair donor", "A hydroxide ion producer"),
            correctIndex = 1,
            explanation = "A Brønsted-Lowry acid is defined as a proton (H⁺) donor.",
            xp = 100
        ),
        Question(
            id = "chem_09",
            subject = "Chemistry",
            topic = "Organic chemistry",
            difficulty = "Extreme",
            question = "Which hydrocarbon family is characterized by a carbon-carbon triple bond?",
            options = listOf("Alkanes", "Alkenes", "Alkynes", "Arenes"),
            correctIndex = 2,
            explanation = "Alkynes have at least one triple bond with general formula CₙH₂ₙ₋₂.",
            xp = 150
        ),
        Question(
            id = "chem_10",
            subject = "Chemistry",
            topic = "Periodic table",
            difficulty = "Easy",
            question = "Which group in the periodic table is known as the Noble Gases?",
            options = listOf("Group 1", "Group 2", "Group 17", "Group 18"),
            correctIndex = 3,
            explanation = "Group 18 elements have full valence electron shells and are inert noble gases.",
            xp = 50
        ),

        // ================= BIOLOGY (10 Questions) =================
        Question(
            id = "bio_01",
            subject = "Biology",
            topic = "Cells",
            difficulty = "Easy",
            question = "Which cellular organelle is known as the powerhouse of the cell?",
            options = listOf("Ribosome", "Endoplasmic reticulum", "Mitochondria", "Golgi apparatus"),
            correctIndex = 2,
            explanation = "Mitochondria generate cellular energy in the form of ATP via cellular respiration.",
            xp = 50
        ),
        Question(
            id = "bio_02",
            subject = "Biology",
            topic = "Genetics",
            difficulty = "Medium",
            question = "In DNA structure, which nitrogenous base pairs with Adenine (A)?",
            options = listOf("Cytosine", "Thymine", "Guanine", "Uracil"),
            correctIndex = 1,
            explanation = "Adenine pairs with Thymine (via two hydrogen bonds) in double-stranded DNA.",
            xp = 75
        ),
        Question(
            id = "bio_03",
            subject = "Biology",
            topic = "Human biology",
            difficulty = "Medium",
            question = "Which blood cells are primarily responsible for transporting oxygen throughout the body?",
            options = listOf("Platelets", "Lymphocytes", "Erythrocytes (Red Blood Cells)", "Neutrophils"),
            correctIndex = 2,
            explanation = "Erythrocytes contain iron-rich hemoglobin that binds and delivers oxygen to tissues.",
            xp = 75
        ),
        Question(
            id = "bio_04",
            subject = "Biology",
            topic = "Ecology",
            difficulty = "Easy",
            question = "What organism forms the foundational base of most marine food webs?",
            options = listOf("Phytoplankton", "Zooplankton", "Small crustaceans", "Apex sharks"),
            correctIndex = 0,
            explanation = "Phytoplankton perform aquatic photosynthesis, forming the primary producer trophic level.",
            xp = 50
        ),
        Question(
            id = "bio_05",
            subject = "Biology",
            topic = "Reproduction",
            difficulty = "Hard",
            question = "What is the cell division process that produces four genetically diverse haploid daughter cells?",
            options = listOf("Mitosis", "Meiosis", "Binary fission", "Budding"),
            correctIndex = 1,
            explanation = "Meiosis reduces chromosome count by half to produce gametes with genetic recombination.",
            xp = 100
        ),
        Question(
            id = "bio_06",
            subject = "Biology",
            topic = "Cells",
            difficulty = "Medium",
            question = "Which molecule is the direct primary chemical currency for cellular work?",
            options = listOf("Glucose", "ATP (Adenosine Triphosphate)", "DNA", "Cellulose"),
            correctIndex = 1,
            explanation = "ATP stores transferable biochemical energy in high-energy phosphate bonds.",
            xp = 75
        ),
        Question(
            id = "bio_07",
            subject = "Biology",
            topic = "Genetics",
            difficulty = "Hard",
            question = "A cross between two heterozygous (Bb) parents produces what phenotypic ratio for a dominant trait?",
            options = listOf("1:1", "1:2:1", "3:1", "9:3:3:1"),
            correctIndex = 2,
            explanation = "Punnett square: BB, Bb, Bb (dominant = 3) vs bb (recessive = 1), giving a 3:1 phenotypic ratio.",
            xp = 100
        ),
        Question(
            id = "bio_08",
            subject = "Biology",
            topic = "Human biology",
            difficulty = "Medium",
            question = "Which organ produces insulin to regulate blood glucose homeostasis?",
            options = listOf("Liver", "Kidney", "Pancreas", "Thyroid"),
            correctIndex = 2,
            explanation = "Beta cells in the islets of Langerhans of the pancreas secrete insulin.",
            xp = 75
        ),
        Question(
            id = "bio_09",
            subject = "Biology",
            topic = "Ecology",
            difficulty = "Extreme",
            question = "What percentage of energy is typically transferred from one trophic level to the next higher level?",
            options = listOf("~10%", "~25%", "~50%", "~90%"),
            correctIndex = 0,
            explanation = "The 10% Rule (Lindeman's efficiency) explains energy loss primarily as metabolic heat.",
            xp = 150
        ),
        Question(
            id = "bio_10",
            subject = "Biology",
            topic = "Cells",
            difficulty = "Easy",
            question = "Which organelle contains digestive enzymes to break down waste materials in animal cells?",
            options = listOf("Lysosome", "Centrosome", "Vacuole", "Nucleolus"),
            correctIndex = 0,
            explanation = "Lysosomes contain acid hydrolases that degrade cellular debris and foreign invaders.",
            xp = 50
        ),

        // ================= ENGLISH (10 Questions) =================
        Question(
            id = "eng_01",
            subject = "English",
            topic = "Grammar",
            difficulty = "Easy",
            question = "Identify the adverb in the following sentence: 'The courageous warrior fought bravely.'",
            options = listOf("courageous", "warrior", "fought", "bravely"),
            correctIndex = 3,
            explanation = "'Bravely' modifies the verb 'fought', answering how the action was performed.",
            xp = 50
        ),
        Question(
            id = "eng_02",
            subject = "English",
            topic = "Vocabulary",
            difficulty = "Medium",
            question = "What is the closest synonym for the word 'EPHEMERAL'?",
            options = listOf("Enduring", "Fleeting", "Gigantic", "Puzzling"),
            correctIndex = 1,
            explanation = "'Ephemeral' means lasting for a very short time; transient or fleeting.",
            xp = 75
        ),
        Question(
            id = "eng_03",
            subject = "English",
            topic = "Sentence structure",
            difficulty = "Medium",
            question = "Which of the following sentences is written in the passive voice?",
            options = listOf(
                "The scientist conducted the experiment.",
                "The storm damaged the communications tower.",
                "The ancient manuscript was discovered by archaeologists.",
                "The students submitted their essays on time."
            ),
            correctIndex = 2,
            explanation = "In passive voice, the subject receives the action ('was discovered by').",
            xp = 75
        ),
        Question(
            id = "eng_04",
            subject = "English",
            topic = "Comprehension",
            difficulty = "Easy",
            question = "What figure of speech is: 'The classroom was an absolute zoo during free period'?",
            options = listOf("Simile", "Metaphor", "Personification", "Hyperbole"),
            correctIndex = 1,
            explanation = "It directly equates two dissimilar things without 'like' or 'as', making it a metaphor.",
            xp = 50
        ),
        Question(
            id = "eng_05",
            subject = "English",
            topic = "Vocabulary",
            difficulty = "Hard",
            question = "What is the antonym of 'PRAGMATIC'?",
            options = listOf("Realistic", "Practical", "Idealistic", "Rational"),
            correctIndex = 2,
            explanation = "'Pragmatic' means dealing with things sensibly; its opposite is 'idealistic' or impractical.",
            xp = 100
        ),
        Question(
            id = "eng_06",
            subject = "English",
            topic = "Grammar",
            difficulty = "Hard",
            question = "Select the grammatically correct sentence:",
            options = listOf(
                "Neither the captain nor the crew members was present.",
                "Neither the captain nor the crew members were present.",
                "Neither the captain nor the crew members is present.",
                "Neither the captain or the crew members were present."
            ),
            correctIndex = 1,
            explanation = "With 'neither... nor', the verb agrees with the subject closest to it ('crew members were').",
            xp = 100
        ),
        Question(
            id = "eng_07",
            subject = "English",
            topic = "Sentence structure",
            difficulty = "Medium",
            question = "What type of sentence is: 'Because the rain was heavy, we postponed the tournament.'?",
            options = listOf("Simple sentence", "Compound sentence", "Complex sentence", "Compound-complex sentence"),
            correctIndex = 2,
            explanation = "It has one dependent clause ('Because the rain was heavy') and one independent clause.",
            xp = 75
        ),
        Question(
            id = "eng_08",
            subject = "English",
            topic = "Vocabulary",
            difficulty = "Extreme",
            question = "What does the Latin root 'VIV' or 'VIT' mean (as in vivid, revitalize)?",
            options = listOf("To conquer", "Life", "To see", "Voice"),
            correctIndex = 1,
            explanation = "The root 'viv/vit' originates from Latin 'vivere' meaning life.",
            xp = 150
        ),
        Question(
            id = "eng_09",
            subject = "English",
            topic = "Grammar",
            difficulty = "Medium",
            question = "Choose the correct pronoun: 'Between you and ___, this battle strategy will work.'",
            options = listOf("I", "me", "myself", "mine"),
            correctIndex = 1,
            explanation = "'Between' is a preposition; prepositions govern the objective case ('me').",
            xp = 75
        ),
        Question(
            id = "eng_10",
            subject = "English",
            topic = "Comprehension",
            difficulty = "Easy",
            question = "What literary device gives human characteristics to non-human entities?",
            options = listOf("Alliteration", "Onomatopoeia", "Personification", "Irony"),
            correctIndex = 2,
            explanation = "Personification attributes human qualities, emotions, or actions to inanimate objects or concepts.",
            xp = 50
        ),

        // ================= SOCIAL STUDIES (10 Questions) =================
        Question(
            id = "soc_01",
            subject = "Social Studies",
            topic = "Government",
            difficulty = "Easy",
            question = "Which branch of government is primarily responsible for making and enacting statutory laws?",
            options = listOf("Executive branch", "Legislative branch", "Judicial branch", "Administrative agency"),
            correctIndex = 1,
            explanation = "The legislative branch (parliament/congress) is vested with the power to draft and pass laws.",
            xp = 50
        ),
        Question(
            id = "soc_02",
            subject = "Social Studies",
            topic = "Economics",
            difficulty = "Medium",
            question = "What fundamental economic principle states that consumers buy more of a good when its price decreases?",
            options = listOf("Law of Supply", "Law of Demand", "Law of Diminishing Returns", "Comparative Advantage"),
            correctIndex = 1,
            explanation = "The Law of Demand states that quantity demanded varies inversely with price, ceteris paribus.",
            xp = 75
        ),
        Question(
            id = "soc_03",
            subject = "Social Studies",
            topic = "Society",
            difficulty = "Medium",
            question = "What term describes the lifelong process through which individuals learn culture, norms, and social roles?",
            options = listOf("Socialization", "Stratification", "Bureaucracy", "Urbanization"),
            correctIndex = 0,
            explanation = "Socialization is the internalizing of society's customs, behaviors, and values.",
            xp = 75
        ),
        Question(
            id = "soc_04",
            subject = "Social Studies",
            topic = "Environment",
            difficulty = "Easy",
            question = "Which international agreement established binding emission targets for greenhouse gases in 2015?",
            options = listOf("Kyoto Protocol", "Paris Agreement", "Geneva Convention", "Montreal Protocol"),
            correctIndex = 1,
            explanation = "The Paris Agreement was adopted in December 2015 at COP21 to combat global climate change.",
            xp = 50
        ),
        Question(
            id = "soc_05",
            subject = "Social Studies",
            topic = "Economics",
            difficulty = "Hard",
            question = "What macroeconomic indicator measures the total monetary value of all finished goods and services produced within a country in a year?",
            options = listOf("Consumer Price Index (CPI)", "Gross Domestic Product (GDP)", "Gini Coefficient", "Purchasing Power Parity (PPP)"),
            correctIndex = 1,
            explanation = "Gross Domestic Product (GDP) represents domestic output within a nation's borders.",
            xp = 100
        ),
        Question(
            id = "soc_06",
            subject = "Social Studies",
            topic = "Government",
            difficulty = "Medium",
            question = "Which system divides governmental power between a national federal government and state/regional governments?",
            options = listOf("Unitary system", "Federalism", "Confederation", "Autocracy"),
            correctIndex = 1,
            explanation = "Federalism divides sovereignty between central and regional/state authorities.",
            xp = 75
        ),
        Question(
            id = "soc_07",
            subject = "Social Studies",
            topic = "Society",
            difficulty = "Hard",
            question = "What term describes the statistical study of human populations, including births, deaths, and migrations?",
            options = listOf("Ethnography", "Demography", "Anthropology", "Sociometry"),
            correctIndex = 1,
            explanation = "Demography is the quantitative study of human populations and demographic trends.",
            xp = 100
        ),
        Question(
            id = "soc_08",
            subject = "Social Studies",
            topic = "Economics",
            difficulty = "Extreme",
            question = "When inflation is paired with stagnant economic growth and high unemployment, the condition is termed:",
            options = listOf("Hyperinflation", "Deflationary spiral", "Stagflation", "Fiscal contraction"),
            correctIndex = 2,
            explanation = "Stagflation combines stagnant economic output with persistent inflation.",
            xp = 150
        ),
        Question(
            id = "soc_09",
            subject = "Social Studies",
            topic = "Environment",
            difficulty = "Medium",
            question = "Which atmospheric layer contains the ozone layer that protects Earth from harmful ultraviolet radiation?",
            options = listOf("Troposphere", "Stratosphere", "Mesosphere", "Thermosphere"),
            correctIndex = 1,
            explanation = "The ozone layer is concentrated in the lower stratosphere, roughly 15 to 35 km above Earth.",
            xp = 75
        ),
        Question(
            id = "soc_10",
            subject = "Social Studies",
            topic = "Government",
            difficulty = "Easy",
            question = "What is the formal document that serves as the supreme law of a nation?",
            options = listOf("Manifesto", "Treaty", "Constitution", "Declaration of Rights"),
            correctIndex = 2,
            explanation = "A constitution establishes the legal foundation and supreme law of the state.",
            xp = 50
        ),

        // ================= ICT / COMPUTER SCIENCE (10 Questions) =================
        Question(
            id = "ict_01",
            subject = "ICT / Computer Science",
            topic = "Programming",
            difficulty = "Easy",
            question = "In programming, which data structure operates on a Last-In, First-Out (LIFO) order?",
            options = listOf("Queue", "Stack", "Linked List", "Binary Tree"),
            correctIndex = 1,
            explanation = "A Stack pushes and pops elements in LIFO order (last in, first out).",
            xp = 50
        ),
        Question(
            id = "ict_02",
            subject = "ICT / Computer Science",
            topic = "Computer hardware",
            difficulty = "Medium",
            question = "Which type of memory is volatile and loses its stored contents when electrical power is switched off?",
            options = listOf("ROM", "Solid State Drive (SSD)", "RAM", "Hard Disk Drive (HDD)"),
            correctIndex = 2,
            explanation = "Random Access Memory (RAM) is volatile main memory requiring continuous power to retain data.",
            xp = 75
        ),
        Question(
            id = "ict_03",
            subject = "ICT / Computer Science",
            topic = "Networks",
            difficulty = "Medium",
            question = "Which standard network protocol translates human-readable domain names (like studybattle.gg) into IP addresses?",
            options = listOf("DHCP", "DNS", "FTP", "SMTP"),
            correctIndex = 1,
            explanation = "The Domain Name System (DNS) acts as the phonebook of the Internet, resolving names to IP addresses.",
            xp = 75
        ),
        Question(
            id = "ict_04",
            subject = "ICT / Computer Science",
            topic = "Cybersecurity",
            difficulty = "Easy",
            question = "What type of deceptive social engineering attack tricks victims into revealing credentials through spoofed emails or websites?",
            options = listOf("Phishing", "DDoS attack", "Buffer overflow", "SQL injection"),
            correctIndex = 0,
            explanation = "Phishing uses fraudulent messages masquerading as trusted sources to steal credentials or sensitive info.",
            xp = 50
        ),
        Question(
            id = "ict_05",
            subject = "ICT / Computer Science",
            topic = "Databases",
            difficulty = "Medium",
            question = "What SQL command is used to retrieve specific columns from a relational database table?",
            options = listOf("EXTRACT", "SELECT", "FETCH", "QUERY"),
            correctIndex = 1,
            explanation = "The SELECT statement queries and retrieves datasets from relational tables in SQL.",
            xp = 75
        ),
        Question(
            id = "ict_06",
            subject = "ICT / Computer Science",
            topic = "Programming",
            difficulty = "Hard",
            question = "What is the worst-case time complexity of standard Binary Search on a sorted array of n elements?",
            options = listOf("O(1)", "O(log n)", "O(n)", "O(n log n)"),
            correctIndex = 1,
            explanation = "Binary search repeatedly halves the search space, resulting in logarithmic O(log n) time complexity.",
            xp = 100
        ),
        Question(
            id = "ict_07",
            subject = "ICT / Computer Science",
            topic = "Networks",
            difficulty = "Hard",
            question = "What is the primary difference between IPv4 and IPv6 address formats?",
            options = listOf(
                "IPv4 uses 32 bits, IPv6 uses 128 bits",
                "IPv4 uses 64 bits, IPv6 uses 256 bits",
                "IPv4 is encrypted, IPv6 is plaintext",
                "IPv4 is wireless only, IPv6 is wired"
            ),
            correctIndex = 0,
            explanation = "IPv4 addresses are 32-bit dotted-decimal numbers, while IPv6 expands to 128-bit hexadecimal.",
            xp = 100
        ),
        Question(
            id = "ict_08",
            subject = "ICT / Computer Science",
            topic = "Cybersecurity",
            difficulty = "Hard",
            question = "In asymmetric cryptography, which key is shared publicly to encrypt messages intended for the recipient?",
            options = listOf("Private key", "Public key", "Master symmetric key", "Hash digest"),
            correctIndex = 1,
            explanation = "Anyone can encrypt with the recipient's public key; only the recipient's matching private key can decrypt.",
            xp = 100
        ),
        Question(
            id = "ict_09",
            subject = "ICT / Computer Science",
            topic = "Databases",
            difficulty = "Extreme",
            question = "In relational database theory, what does ACID stand for?",
            options = listOf(
                "Atomicity, Consistency, Isolation, Durability",
                "Access, Control, Integrity, Data",
                "Authentication, Cipher, Identity, Directory",
                "Array, Cluster, Index, Distribution"
            ),
            correctIndex = 0,
            explanation = "ACID properties ensure reliable processing of database transactions.",
            xp = 150
        ),
        Question(
            id = "ict_10",
            subject = "ICT / Computer Science",
            topic = "Computer hardware",
            difficulty = "Easy",
            question = "Which component is widely known as the 'brain' of the computer, executing program instructions?",
            options = listOf("Power Supply Unit (PSU)", "Graphics Card (GPU)", "Central Processing Unit (CPU)", "Motherboard"),
            correctIndex = 2,
            explanation = "The CPU (Central Processing Unit) performs basic arithmetic, logic, controlling, and I/O operations.",
            xp = 50
        )
    )

    fun getQuestions(
        subject: String?,
        topic: String? = null,
        difficulty: String? = null,
        limit: Int = 10
    ): List<Question> {
        var pool = ALL_QUESTIONS
        if (!subject.isNullOrBlank() && subject != "All Subjects") {
            pool = pool.filter { it.subject.equals(subject, ignoreCase = true) }
        }
        if (!topic.isNullOrBlank() && topic != "All Topics") {
            pool = pool.filter { it.topic.equals(topic, ignoreCase = true) }
        }
        if (!difficulty.isNullOrBlank() && difficulty != "All Difficulties") {
            pool = pool.filter { it.difficulty.equals(difficulty, ignoreCase = true) }
        }

        // Randomize order and randomize options order while keeping correct index accurate!
        val randomizedList = pool.shuffled().take(limit).map { q ->
            val correctText = q.options[q.correctIndex]
            val shuffledOptions = q.options.shuffled()
            val newCorrectIndex = shuffledOptions.indexOf(correctText)
            q.copy(
                options = shuffledOptions,
                correctIndex = newCorrectIndex
            )
        }

        return if (randomizedList.isEmpty()) {
            ALL_QUESTIONS.shuffled().take(limit)
        } else {
            randomizedList
        }
    }

    fun getTopicsForSubject(subject: String): List<String> {
        return ALL_QUESTIONS.filter { it.subject.equals(subject, ignoreCase = true) }
            .map { it.topic }
            .distinct()
    }
}
