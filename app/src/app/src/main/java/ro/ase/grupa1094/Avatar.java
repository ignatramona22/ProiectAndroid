package ro.ase.grupa1094;

public class Avatar {
    private int avatarId;
    private String avatarName;

    public Avatar(int avatarId, String avatarName)
    {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getAvatarName() {
        return avatarName;
    }
}
