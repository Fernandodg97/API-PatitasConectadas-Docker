package net.xeill.elpuig.apipatitasconectadas.services;

import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioPostModelDtoRequest;
import net.xeill.elpuig.apipatitasconectadas.controllers.dto.UsuarioPostModelDtoResponse;
import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioPostModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.PostRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UserRepository;
import net.xeill.elpuig.apipatitasconectadas.repositories.UsuarioPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioPostService {

    @Autowired
    private UsuarioPostRepository usuarioPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // Obtener todas las relaciones usuario-post
    public List<UsuarioPostModelDtoResponse> getAllUsuarioPosts() {
        return usuarioPostRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener una relación específica por ID
    public UsuarioPostModelDtoResponse getUsuarioPostById(Long id) {
        UsuarioPostModel usuarioPost = usuarioPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación usuario-post no encontrada"));
        return convertToDto(usuarioPost);
    }

    // Obtener todas las relaciones de un usuario específico
    public List<UsuarioPostModelDtoResponse> getUsuarioPostsByUserId(Long userId) {
        return usuarioPostRepository.findByUsuarioId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener todas las relaciones de un post específico
    public List<UsuarioPostModelDtoResponse> getUsuarioPostsByPostId(Long postId) {
        return usuarioPostRepository.findByPostId(postId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Crear una nueva relación usuario-post
    @Transactional
    public UsuarioPostModelDtoResponse createUsuarioPost(UsuarioPostModelDtoRequest request) {
        UserModel usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        PostModel post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        // Verificar si ya existe la relación
        if (usuarioPostRepository.existsByUsuarioAndPost(usuario, post)) {
            throw new RuntimeException("Ya existe una relación entre este usuario y post");
        }

        UsuarioPostModel usuarioPost = new UsuarioPostModel();
        usuarioPost.setUsuario(usuario);
        usuarioPost.setPost(post);

        UsuarioPostModel savedUsuarioPost = usuarioPostRepository.save(usuarioPost);
        return convertToDto(savedUsuarioPost);
    }

    // Eliminar una relación usuario-post
    @Transactional
    public void deleteUsuarioPost(Long id) {
        if (!usuarioPostRepository.existsById(id)) {
            throw new RuntimeException("Relación usuario-post no encontrada");
        }
        usuarioPostRepository.deleteById(id);
    }

    // Eliminar todas las relaciones de un usuario
    @Transactional
    public void deleteAllUsuarioPostsByUserId(Long userId) {
        usuarioPostRepository.deleteByUsuarioId(userId);
    }

    // Eliminar todas las relaciones de un post
    @Transactional
    public void deleteAllUsuarioPostsByPostId(Long postId) {
        usuarioPostRepository.deleteByPostId(postId);
    }

    // Método auxiliar para convertir de Model a DTO
    private UsuarioPostModelDtoResponse convertToDto(UsuarioPostModel usuarioPost) {
        UsuarioPostModelDtoResponse dto = new UsuarioPostModelDtoResponse();
        dto.setId(usuarioPost.getId());
        dto.setUsuarioId(usuarioPost.getUsuario().getId());
        dto.setPostId(usuarioPost.getPost().getId());
        dto.setFecha(usuarioPost.getFecha());
        return dto;
    }
} 