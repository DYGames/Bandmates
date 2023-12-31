package com.dygames.bandmates.domain.repository

import com.dygames.bandmates.domain.member.Member
import com.dygames.bandmates.domain.project.Project

interface ProjectRepository : BaseRepository<Project, Long> {
    fun findAllByOwner(owner: Member): List<Project>
}
