package com.dygames.bandmates.service

import com.dygames.bandmates.domain.project.Project
import com.dygames.bandmates.domain.project.Tracks
import com.dygames.bandmates.domain.project.User
import com.dygames.bandmates.domain.project.repository.ProjectRepository
import com.dygames.bandmates.domain.project.repository.UserRepository
import com.dygames.bandmates.service.dto.ProjectRequest
import com.dygames.bandmates.service.dto.ProjectResponse
import com.dygames.bandmates.service.dto.ProjectsResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ProjectService(
    private val projectRepository: ProjectRepository, private val userRepository: UserRepository
) {

    @Transactional
    fun findAll(): ProjectsResponse {
        val projects = projectRepository.findAll()
        return ProjectsResponse.of(projects.toList())
    }

    @Transactional
    fun create(userId: Long, request: ProjectRequest): ProjectResponse {
        val author = User(
            request.author.name, request.author.email
        )
        val project = Project(
            author = author, owner = author, tracks = Tracks(emptyList())
        )
        return ProjectResponse.of(
            projectRepository.save(project)
        )
    }

    @Transactional
    fun delete(id: Long) {
        projectRepository.deleteById(id)
    }

    @Transactional
    fun fork(userId: Long, projectId: Long): ProjectResponse {
        val project = projectRepository.findById(projectId).get()
        val owner = userRepository.findById(userId).get()
        val forkedProject = Project(
            author = project.author, owner = owner, tracks = project.tracks
        )
        return ProjectResponse.of(
            projectRepository.save(forkedProject)
        )
    }
}
