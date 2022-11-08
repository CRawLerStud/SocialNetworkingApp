package repo.friendship;

import models.Friendship;
import repo.RepositoryException;

import java.io.FileWriter;
import java.io.IOException;

public class FriendshipFileRepository extends FriendshipRepository {

    private final String filename;

    public FriendshipFileRepository(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void save(Friendship entity) throws RepositoryException {
        super.save(entity);
        writeToFile();
    }

    @Override
    public Friendship delete(Long aLong) throws RepositoryException {
        Friendship deletedFriendship =  super.delete(aLong);
        writeToFile();
        return deletedFriendship;
    }

    private void writeToFile(){
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("");
            for (Friendship friendship : this.findAll()) {
                fw.append(friendship.toString()).append("\n");
            }
            fw.close();
        }
        catch(IOException e){
            System.err.println("Error while writing in the friendship file!");
            e.printStackTrace();
        }
    }
}
