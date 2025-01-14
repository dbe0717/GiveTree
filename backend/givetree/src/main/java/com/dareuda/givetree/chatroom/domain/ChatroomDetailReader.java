package com.dareuda.givetree.chatroom.domain;

import com.dareuda.givetree.chatroom.domain.dto.ChatroomDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatroomDetailReader {
    private final ChatroomReader chatroomReader;
    private final ChatroomHistoryReader chatroomHistoryReader;
    private final ChatroomConnectionReader chatroomConnectionReader;

    @Transactional(readOnly = true)
    public ChatroomDetail read(long chatroomId, long memberId) {
        Chatroom chatroom = chatroomReader.read(chatroomId);

        ChatroomHistory lastHistory = chatroomHistoryReader.readLastHistoryByChatroom(chatroomId);
        ChatroomConnection connection = chatroomConnectionReader.readCounterpartConnection(memberId, chatroomId);

        String profileImageUrl = connection.getMember().getProfileImage() != null
                ? connection.getMember().getProfileImage().getUrl() : null;

        return ChatroomDetail.builder()
                .id(chatroom.getId())
                .saleId(chatroom.getSale().getId())
                .saleTitle(chatroom.getSale().getTitle())
                .counterpartId(connection.getMember().getId())
                .counterpartName(connection.getMember().getName())
                .counterpartProfileImageUrl(profileImageUrl)
                .lastMessage(lastHistory != null ? lastHistory.getMessage() : null)
                .lastMessageCreatedAt(lastHistory != null ? lastHistory.getCreatedAt() : chatroom.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatroomDetail> readByMember(long memberId) {
        return chatroomReader.readByMember(memberId).stream()
                .map(chatroom -> read(chatroom.getId(), memberId))
                .sorted((o1, o2) -> {
                    if (o1.getLastMessageCreatedAt().isAfter(o2.getLastMessageCreatedAt())) return -1;
                    if (o1.getLastMessageCreatedAt().isBefore(o2.getLastMessageCreatedAt())) return 1;
                    return 0;
                })
                .toList();
    }
}
