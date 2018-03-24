import igraph as ig

# read graph
print("Reading")
g = ig.Graph.Read_Ncol("data/communities.csv", directed = False)

# analise
print("1 - Infomap")
print("2 - Walktrap")
print("3 - FastGreedy")
print("4 - Multilevel")
print("5 - Label Propagation")
print("6 - Eigenvector")
option = int(input("Choose: "))

print("Analising")
if option == 1:
    communities = g.community_infomap(trials = 10)
if option == 2:
    communities = g.community_walktrap().as_clustering()
if option == 3:
    communities = g.community_fastgreedy().as_clustering()
if option == 4:    
    communities = g.community_multilevel()
if option == 5:    
    communities = g.community_label_propagation()
if option == 6:    
    communities = g.community_leading_eigenvector()
    

# print communities
for community in communities:
    # get all node names
    names = [g.vs[vertex]["name"] for vertex in community]
    
    # count how many charateristics nodes are in the community
    number_of_characteristics = len([name for name in names if not name.startswith("game-")])

    # print only communities with more than one characteristic node
    if number_of_characteristics > 1:
        print("========================================")
        print("Community size".ljust(40) + " = " + str(len(community)))    
        for name in names:
            if not name.startswith("game-"):            
                print(name)
        input("")