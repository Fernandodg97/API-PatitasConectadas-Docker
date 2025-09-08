package net.xeill.elpuig.apipatitasconectadas.services;

import net.xeill.elpuig.apipatitasconectadas.models.EventoModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.UsuarioEventoModel;
import net.xeill.elpuig.apipatitasconectadas.repositories.UsuarioEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioEventoService {

    @Autowired
    private UsuarioEventoRepository usuarioEventoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventoService eventoService;

    public List<UsuarioEventoModel> getAllUsuarioEventos() {
        return usuarioEventoRepository.findAll();
    }

    public UsuarioEventoModel getUsuarioEventoById(Long id) {
        return usuarioEventoRepository.findById(id).orElse(null);
    }

    public List<UsuarioEventoModel> getUsuarioEventosByUsuarioId(Long usuarioId) {
        return usuarioEventoRepository.findByUsuarioId(usuarioId);
    }

    public List<UsuarioEventoModel> getUsuarioEventosByEventoId(Long eventoId) {
        return usuarioEventoRepository.findByEventoId(eventoId);
    }

    public UsuarioEventoModel saveUsuarioEvento(UsuarioEventoModel usuarioEvento) {
        // Verificar que el usuario y el evento existen
        UserModel usuario = userService.getUserById(usuarioEvento.getUsuario().getId());
        EventoModel evento = eventoService.getEventoById(usuarioEvento.getEvento().getId());

        if (usuario == null || evento == null) {
            return null;
        }

        // Verificar si ya existe la relación
        if (usuarioEventoRepository.existsByUsuarioIdAndEventoId(usuario.getId(), evento.getId())) {
            return null;
        }

        return usuarioEventoRepository.save(usuarioEvento);
    }

    public void deleteUsuarioEvento(Long id) {
        usuarioEventoRepository.deleteById(id);
    }

    public void deleteUsuarioEventosByUsuarioId(Long usuarioId) {
        usuarioEventoRepository.deleteByUsuarioId(usuarioId);
    }

    public void deleteUsuarioEventosByEventoId(Long eventoId) {
        usuarioEventoRepository.deleteByEventoId(eventoId);
    }

    public void deleteUsuarioEventoByUsuarioIdAndEventoId(Long usuarioId, Long eventoId) {
        usuarioEventoRepository.deleteByUsuarioIdAndEventoId(usuarioId, eventoId);
    }

    public boolean existsUsuarioEvento(Long usuarioId, Long eventoId) {
        return usuarioEventoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId);
    }

    public Optional<UsuarioEventoModel> getUsuarioEventoByUsuarioIdAndEventoId(Long usuarioId, Long eventoId) {
        return usuarioEventoRepository.findByUsuarioIdAndEventoId(usuarioId, eventoId);
    }
} 