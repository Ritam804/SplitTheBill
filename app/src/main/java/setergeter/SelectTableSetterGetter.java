package setergeter;

/**
 * Created by ritam on 05/05/17.
 */

public class SelectTableSetterGetter {

    String TableName, Cost, MinAmount, TableStatus, NumBerOfAvailableSit, TotalNumberOfSit, WhatToDo, TableId, HostName, HostId, GroupId, RemainingAmount,VenueName;
    int BookingStatus,DeclineStatus,RequestStatus;

    public SelectTableSetterGetter(String tableName, String cost, String minAmount, String tableStatus, String numBerOfAvailableSit, String totalNumberOfSit, String whatToDo, String tableId, String hostname, String hostid, int bookingstatus, int declineStatus, int requeststatus, String groupid, String remainingAmount, String venuename) {
        TableName = tableName;
        Cost = cost;
        MinAmount = minAmount;
        TableStatus = tableStatus;
        NumBerOfAvailableSit = numBerOfAvailableSit;
        TotalNumberOfSit = totalNumberOfSit;
        WhatToDo = whatToDo;
        TableId = tableId;
        HostName = hostname;
        HostId = hostid;
        BookingStatus = bookingstatus;
        DeclineStatus = declineStatus;
        RequestStatus = requeststatus;
        GroupId = groupid;
        RemainingAmount = remainingAmount;
        VenueName = venuename;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getRemainingAmount() {
        return RemainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        RemainingAmount = remainingAmount;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public int getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        RequestStatus = requestStatus;
    }

    public int getDeclineStatus() {
        return DeclineStatus;
    }

    public void setDeclineStatus(int declineStatus) {
        DeclineStatus = declineStatus;
    }

    public int getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public String getTableId() {
        return TableId;
    }

    public void setTableId(String tableId) {
        TableId = tableId;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(String minAmount) {
        MinAmount = minAmount;
    }

    public String getTableStatus() {
        return TableStatus;
    }

    public void setTableStatus(String tableStatus) {
        TableStatus = tableStatus;
    }

    public String getNumBerOfAvailableSit() {
        return NumBerOfAvailableSit;
    }

    public void setNumBerOfAvailableSit(String numBerOfAvailableSit) {
        NumBerOfAvailableSit = numBerOfAvailableSit;
    }

    public String getTotalNumberOfSit() {
        return TotalNumberOfSit;
    }

    public void setTotalNumberOfSit(String totalNumberOfSit) {
        TotalNumberOfSit = totalNumberOfSit;
    }

    public String getWhatToDo() {
        return WhatToDo;
    }

    public void setWhatToDo(String whatToDo) {
        WhatToDo = whatToDo;
    }
}
