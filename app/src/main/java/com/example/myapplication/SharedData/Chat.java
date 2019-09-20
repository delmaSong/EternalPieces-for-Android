package com.example.myapplication.SharedData;

/**
 * Created by Delma Song on 2019-05-12
 */
public class Chat {

    String myId, yourId, message, chat_design;      //내 아이디, 상대방 아이디, 메세지, 상담 도안
    int chatRoomId;     //채팅방 id.,,


    public Chat() {
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getYourId() {
        return yourId;
    }

    public void setYourId(String yourId) {
        this.yourId = yourId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChat_design() {
        return chat_design;
    }

    public void setChat_design(String chat_design) {
        this.chat_design = chat_design;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "myId='" + myId + '\'' +
                ", yourId='" + yourId + '\'' +
                ", message='" + message + '\'' +
                ", chat_design='" + chat_design + '\'' +
                ", chatRoomId=" + chatRoomId +
                '}';
    }
}
