package Client;


import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class is used to send messages to the server.
 */
public class ClientMessageSender implements Runnable {
    static Scanner input = new Scanner(System.in);
    private static ObjectOutputStream out;
    private static String sender;
    private static String secondData;
    private static String thirdData;
    private String messageKind;

    public ClientMessageSender(ObjectOutputStream out, String sender, String secondData,String thirdData, String messageKind) {
        this.out = out;
        this.sender = sender;
        this.secondData = secondData;
        this.thirdData = thirdData;
        this.messageKind = messageKind;

    }

    @Override
    public void run() {
        if (messageKind.equals("private")) {
            //send a request to server for chat history
            sendRequest(sender, secondData);
            while (true) {
                //input the context and send it
                String inp = input.nextLine();
                if (Objects.equals(inp, "exit")) {
                    break;
                }
                sendPrivateMessage(sender, inp, secondData);
            }
        } else if (messageKind.equals("friendRequest")) {
            sendFriendRequest(sender, secondData);
        } else if (messageKind.equals("friendRequestHistory")){
            sendFriendRequestHistory(sender);
            while (true) {
                String inp = input.nextLine();
                if (Objects.equals(inp, "exit")) {
                    break;
                }
                answerFriendRequest(sender,inp.split(" ")[1],inp.split(" ")[0]);
            }
        }else if(messageKind.equals("requestFriendList")){
            sendFriendListRequest(sender);
            while (true){
                String inp = input.nextLine();
                if (Objects.equals(inp, "exit")) {
                    break;
                }
            }
        }else if(messageKind.equals("changeStateRequest")){
            String state = "";
            System.out.println("""
                        Choose one of below:
                        1- Online
                        2- Idle
                        3- Do Not Disturb
                        4- Invisible""");
            while (true) {
                String inp = input.nextLine();
                if (inp.equals("1")) {
                    state = "Online";
                } else if (inp.equals("2")) {
                    state = "Idle";
                } else if (inp.equals("3")) {
                    state = "Do Not Disturb";
                } else if (inp.equals("4")) {
                    state = "Offline";
                }
                if (Objects.equals(inp, "exit")) {
                    break;
                }
                sendChangeStateRequest(sender, state);
            }
        }
        else if(messageKind.equals("blockRequest")){
            sendBlockRequest(sender, secondData);
        }
        else if (messageKind.equals("createGroupRequest")){
            createGroupRequest(sender, secondData);
        }
        else if (messageKind.equals("showGroupsRequest")){
            showGroups(sender);
        }
        else if(messageKind.equals("changePasswordRequest")){
            changePassword(sender, secondData);
        }
        else if(messageKind.equals("signInGroupRequest")){
            signInGroup(sender, secondData);
        }
        else if (messageKind.equals("showAbilitiesRequest")){
            showAbilities(sender,secondData);
        }
        else if(messageKind.equals("addUserToGroupRequest")){
            addToGroup(sender,secondData,thirdData);
        }
        else if (messageKind.equals("showGivableAbilitiesRequest")){
            showGivalbeAbilities();
        }
        else if (messageKind.equals("createRoleRequest")){
            createRole(sender,secondData,thirdData);
        }
        else if (messageKind.equals("addAbilityToRoleRequest")){
            addAbilityToRole(sender,secondData,thirdData);
        }
        else if(messageKind.equals("createChannelRequest")){
            createChannel(sender,secondData,thirdData);
        }
//        else if(messageKind.equals("showChannelsRequest")){
//            showChannels(sender,secondData);
//        }
//        else if (messageKind.equals("signInChannelRequest")){
//            signInChannel(sender,secondData,thirdData);
//        }
//        else if (messageKind.equals("channelChat")){
//            while (true) {
//                //input the context and send it
//
//                String inp = input.nextLine();
//                if (Objects.equals(inp, "exit")) {
//                    break;
//                }
//                sendChannelMessage(sender, inp, thirdData + '-' + secondData);
//            }
//        }
//        else if (messageKind.equals("showGroupMembersRequest")){
//            showMembers(sender,thirdData);
//        }
//        else if (messageKind.equals("showRolesRequest")){
//            showRoles(sender,thirdData);
//        }
//        else if (messageKind.equals("defineRoleRequest")){
//            defineRole(sender,secondData,thirdData);
//        }
//        else if(messageKind.equals("logOutRequest")){
//            logOut();
//        }
//        else if(messageKind.equals("renameGroupRequest")){
//            renameGroup(sender,secondData,thirdData);
//        }
//        else if(messageKind.equals("deleteChannelRequest")){
//            deleteChannel(sender,secondData,thirdData);
//        }
//        else if(messageKind.equals("deleteGroupRequest")){
//            deleteGroup(sender,thirdData);
//        }
//        else if(messageKind.equals("removeUserRequest")){
//            removeUser(sender,secondData,thirdData);
//        }
//        else if(messageKind.equals("changeProfileRequest")){
//            changeProfile(sender);
//        }
//        else if(messageKind.equals("showBannableUsersRequest")){
//            showBannableUsers(sender,secondData,thirdData);
//        }
//        else if(messageKind.equals("banUserRequest")){
//            banUser(sender,secondData,thirdData);
//        }
    }

    /**
     * @param receiver the client object
     * @param context  the message object
     */
    public static void sendPrivateMessage(String sender, String context, String receiver) {
        //if the sender is blocked can not send message
        //Message message = new Message(receiver, context, sender, "private");
       // try {
         //   out.writeObject(message);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    /**
     * This method is used for sending friend request
     */
    public static void sendFriendRequest(String sender, String receiver) {
       // Message message = new Message(sender, null, receiver, "friendRequest");
//        try {
//            out.writeObject(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void answerFriendRequest(String sender, String answer, String receiver){
//        Message message = new Message(sender,answer,receiver,"friendRequestResponse");
//        try {
//            out.writeObject(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used to see the history of chats
     * @param sender
     * @param receiver
     */
    public static void sendRequest(String sender, String receiver) {
//        try {
//            out.writeObject(new Message(sender, "", receiver, "historyRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    }


    public static void sendFriendRequestHistory(String sender){
//        try {
//            out.writeObject(new Message(sender, "", "", "friendRequestHistory"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used for seeing friend requests list
     * @param sender is the client who wants to see friend request list
     */
    public static void sendFriendListRequest(String sender){
//        try {
//            out.writeObject(new Message(sender, "", "", "requestFriendList"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used to change state of a client
     * @param sender is the client whose state is changed
     * @param state is the new state
     */
    public static void sendChangeStateRequest(String sender,String state){
//        try {
//            out.writeObject(new Message(sender,state,null,"changeStateRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used to block a client
     * @param sender is the client who wants to block another client
     * @param receiver is the client who is going to be blocked
     */
    public static void sendBlockRequest(String sender,String receiver){
//        try {
//            out.writeObject(new Message(sender,null,receiver,"blockRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used to create a new group
     * @param sender is the client who is going to create a new group
     * @param name is the name of new group
     */
    public static void createGroupRequest(String sender,String name){
//        try {
//            out.writeObject(new Message(sender,name,null,"createGroupRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used for showing groups of a client
     * @param sender is the client whose groups are showed
     */
    public static void showGroups(String sender){
//        try {
//            out.writeObject(new Message(sender,null,null,"showGroupsRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * This method is used for changing password
     * @param sender is the client whose password is going to change
     * @param password is the new password
     */
    public static void changePassword(String sender,String password){
//        try {
//            out.writeObject(new Message(sender,password,null,"changePasswordRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void signInGroup(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,groupName,null,"signInGroupRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void showAbilities(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,groupName,null,"showAbilitiesRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void addToGroup(String sender,String groupName,String newUser){
//        try {
//            out.writeObject(new Message(sender,groupName,newUser,"addUserToGroupRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void showGivalbeAbilities(){
//        try {
//            out.writeObject(new Message(null,null,null,"showGivableAbilitiesRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void createRole(String sender,String roleName, String groupName){
//        try {
//            out.writeObject(new Message(sender,roleName,groupName,"createRoleRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void addAbilityToRole(String sender, String roleAndAbility, String groupName) {
//        try {
//            out.writeObject(new Message(sender,roleAndAbility,groupName,"addAbilityToRoleRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void createChannel(String sender,String channel,String groupName){
//        try {
//            out.writeObject(new Message(sender,channel,groupName,"createChannelRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public static void showChannels(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,null,groupName,"showChannelsRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void signInChannel(String sender, String channelName, String groupName){
//        try {
//            out.writeObject(new Message(sender,channelName,groupName,"signInChannelRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendChannelMessage(String sender,String message,String groupAndChannel){
//        try {
//            out.writeObject(new Message(sender,message,groupAndChannel,"channelChat"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void  showMembers(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,null,groupName,"showGroupMembersRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void showRoles(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,null,groupName,"showRolesRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void defineRole(String sender,String memberAndRole,String groupName){
//        try {
//            out.writeObject(new Message(sender,memberAndRole,groupName,"defineRoleRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void logOut(){
//        try {
//            out.writeObject(new Message(null,null,null,"logOutRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void renameGroup(String sender,String oldName,String newName){
//        try {
//            out.writeObject(new Message(sender,oldName,newName,"renameGroupRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void deleteChannel(String sender,String channelName,String groupName){
//        try {
//            out.writeObject(new Message(sender,channelName,groupName,"deleteChannelRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void deleteGroup(String sender,String groupName){
//        try {
//            out.writeObject(new Message(sender,null,groupName,"deleteGroupRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void removeUser(String sender,String username,String groupName){
//        try {
//            out.writeObject(new Message(sender,username,groupName,"removeUserRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void changeProfile(String sender){
//        try {
//            out.writeObject(new Message(sender,null,null,"changeProfileRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void showBannableUsers(String sender,String channelName,String groupName){
//        try {
//            out.writeObject(new Message(sender,channelName,groupName,"showBannableUsersRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void banUser(String sender,String username,String groupAndChannel){
//        try {
//            out.writeObject(new Message(sender,username,groupAndChannel,"banUserRequest"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
