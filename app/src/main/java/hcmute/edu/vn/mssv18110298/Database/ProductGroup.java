package hcmute.edu.vn.mssv18110298.Database;

public class ProductGroup {
    int GroupID;
    String GroupName;

    public ProductGroup()
    {
        super();
        this.GroupID = GroupID;
        this.GroupName = GroupName;
    }

    public ProductGroup(String GroupName)
    {
        super();
        this.GroupName = GroupName;
    }

    public int getGroupID()
    {
        return GroupID;
    }

    public void setGroupID(int GroupID)
    {
        this.GroupID = GroupID;
    }

    public String getGroupName()
    {
        return GroupName;
    }

    public void setGroupName(String GroupName)
    {
        this.GroupName = GroupName;
    }
}
