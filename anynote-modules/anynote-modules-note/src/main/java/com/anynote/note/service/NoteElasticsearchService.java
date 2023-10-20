package com.anynote.note.service;

import co.elastic.clients.elasticsearch.core.DeleteResponse;

public interface NoteElasticsearchService {

    public Integer deleteNoteIndex(Long noteId);
}
