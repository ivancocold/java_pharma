# java_pharma

Ce travail est le rendu d'un projet scolaire. Il répond au cahier de charge suivant.

" Nous allons gérer la vente de médicaments. Pour cela nous aurons les tables suivantes :
• Medicament (idMedicament, designationMed,effetMed, posologieMed,prixLaboratoire)
• Vendeur (idVendeur, nom Vendeur, adrVendeur,telVendeur)
• Vends (idVendeur, idMedicament, prixVente)
• Medecin (idMedecin, nomMedecin, specialiteMedecin, adrMedecin)
• Prescrire (idMedecin, idMedicament)

Ainsi que les écrans suivants:

• 1 écran de menu qui demandera ce que souhaite faire l'utilisateur (via une liste déroulante) : Voir les médicaments existants, voir les vendeurs existants, ajouter un médicament

• 1 écran par possibilité du menu

• Sur l'écran listant les médicaments existants, donner la possibilité de sélectionner un médicament et de voir les médecins qui l'ont prescrit ( ensemble des informations du médecin) ou les vendeurs qui le fournisse (ensemble des informations sur le vendeur) avec le prix de vente. Attention, on ne devra avoir cette possibilité que lorsqu'on aura sélectionné un médicament.

• Sur l'écran des vendeurs, donner la possibilité de modifier les informations d'un vendeur sélectionné

Vous utiliserez au moins une fois un des composants suivants : liste, liste déroulante, bouton, texte modifiable.
Indication:

• Vous pouvez rendre visible ou invisible votre fenêtre à l'aide de la méthode setVisible.

• En Java, un tableau a une taille fixe, celle que vous indiquez lors de sa création. Pour les besoins de ce TP et la création de liste, vous pouvez créer un tableau de taille assez grande afin d'y insérer les données avant liaison avec la liste.


Syntaxe: String nomTableau = new String[taille] ;
nomTableau[indice ]=valeur;"
