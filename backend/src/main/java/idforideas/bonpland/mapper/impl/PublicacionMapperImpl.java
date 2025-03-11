//
//package idforideas.bonpland.mapper.impl;
//
//import idforideas.bonpland.dto.PublicacionDTO;
//import idforideas.bonpland.entities.Publicacion;
//import idforideas.bonpland.entities.Usuario;
//import idforideas.bonpland.exception.UsuarioNotFoundException;
//import idforideas.bonpland.mapper.PublicacionMapper;
//import idforideas.bonpland.repository.UsuarioRepository;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author Martina
// */
//@Component
//public class PublicacionMapperImpl implements PublicacionMapper{
//    LocalDateTime hoy = LocalDateTime.now();
//     DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//
//     @Autowired
//     private UsuarioRepository usuarioRepository;
//
//    @Override
//    public Publicacion dtoAentidad(PublicacionDTO dto) {
//        Publicacion p=new Publicacion();
//        p.setTitulo(dto.getTitulo());
//        p.setDescripcion(dto.getDescripcion());
//        //metodo para obtener la fecha actual
//        p.setFechaPublicacion(ingresarFecha());
//        p.setPrecio(dto.getPrecio());
//        p.setEstado(dto.getEstado());
//        p.setTipoMoneda(dto.getTipoMoneda());
//        p.setTipoOperacion(dto.getTipoOperacion());
//
//        Usuario ingresado = usuarioRepository.findById(dto.getIdUsuario())
//    .orElseThrow(() -> new UsuarioNotFoundException("El usuario no existe"));
//
//
//        //p.setInmueble(dto.getInmueble());
//        p.setUsuario(ingresado);
//        return p;
//    }
//
//    public Date ingresarFecha(){
//        String fechaNueva=hoy.format(formato);
//         LocalDateTime actual=LocalDateTime.parse(fechaNueva, formato);
//         //String formatoFechaMostrar=mostrarHora(actual);
//         Date fechaDate = Date.from(actual.atZone(ZoneId.systemDefault()).toInstant());
//         return fechaDate;
//
//    }
//
//}
