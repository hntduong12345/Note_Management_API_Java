package com.note_management_hub.Note_Management_Hub_API.repositories;

import com.note_management_hub.Note_Management_Hub_API.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
