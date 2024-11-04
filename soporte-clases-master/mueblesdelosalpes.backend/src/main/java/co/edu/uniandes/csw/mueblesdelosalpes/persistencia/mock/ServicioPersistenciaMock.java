package co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.ExperienciaVendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.RegistroVenta;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.TipoMueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.TipoUsuario;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockRemote;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Stateless
public class ServicioPersistenciaMock implements IServicioPersistenciaMockRemote, IServicioPersistenciaMockLocal {

    private static List<Vendedor> vendedores;
    private static List<Mueble> muebles;
    private static List<Usuario> usuarios;
    private static List<RegistroVenta> registrosVentas;

    public ServicioPersistenciaMock() {
        if (vendedores == null) {
            inicializarDatos();
        }
    }

    private void inicializarDatos() {
        vendedores = new ArrayList<>();
        muebles = new ArrayList<>();
        usuarios = new ArrayList<>();
        registrosVentas = new ArrayList<>();

        ArrayList<ExperienciaVendedor> experiencia = new ArrayList<>();

        experiencia.add(new ExperienciaVendedor(1L, "Banco de los Alpes", "Cajero", "Se desempeñó en diferentes áreas administrativas", 1998));
        vendedores.add(new Vendedor(1L, "Carlos Antonio", "Gomez Rodriguez", experiencia, 900000, 80000, "Técnico en auditoría y costos", "vendedor1"));

        experiencia.clear();
        experiencia.add(new ExperienciaVendedor(2L, "Marketplace de los Alpes", "Asesor de ventas", "Se desempeñó cómo consultor y asesor en área de ventas", 2006));
        vendedores.add(new Vendedor(2L, "Claudia", "Sanchez Guerrero", experiencia, 950000, 85000, "Comunicadora social", "vendedor2"));

        experiencia.clear();
        experiencia.add(new ExperienciaVendedor(3L, "Seguros de los Alpes", "Vendedor", "Se desempeñó como vendedora e impulsadora", 2010));
        vendedores.add(new Vendedor(3L, "Angela Patricia", "Montoya Zanabria", experiencia, 1200000, 135000, "Técnico en Gestión de mercadeo", "vendedor2"));

        experiencia.clear();
        experiencia.add(new ExperienciaVendedor(4L, "Autopartes de los Alpes", "Director de producción", "Se desempeñó cómo director en el área de producción", 2009));
        vendedores.add(new Vendedor(4L, "Juan Pablo", "Escobar Vélez", experiencia, 1000000, 100000, "Técnico en métodos de producción", "vendedor1"));

        muebles.add(new Mueble(1L, "Silla clásica", "Una confortable silla con estilo del siglo XIX.", TipoMueble.Interior, 45, "sillaClasica", 123));
        muebles.add(new Mueble(2L, "Sillón new wave", "Innovador y cómodo. No existen mejores palabras para describir este hermoso sillón.", TipoMueble.Interior, 60, "newWave", 5655));
        muebles.add(new Mueble(3L, "Silla moderna", "Lo último en la moda de interiores. Esta silla le brindará la comodidad e innovación que busca", TipoMueble.Interior, 50, "sillaModerna", 5464));
        muebles.add(new Mueble(4L, "Mesa de jardín", "Una bella mesa para comidas y reuniones al aire libre.", TipoMueble.Exterior, 100, "mesaJardin", 4568));
        muebles.add(new Mueble(5L, "Orange games", "Una hermosa silla con un toqué moderno y elegante. Excelente para su sala de estar", TipoMueble.Interior, 70, "sillaNaranja", 1345));
        muebles.add(new Mueble(6L, "Cama king", "Una hermosa cama hecha en cedro para dos personas. Sus sueños no volveran a ser iguales.", TipoMueble.Interior, 50, "bed", 63358));
        muebles.add(new Mueble(7L, "Silla Neoclásica", "Una bella silla con un estilo neoclásico", TipoMueble.Exterior, 65, "neoClasica", 678));
        muebles.add(new Mueble(8L, "Camarote junior", "Con diseño moderno. Sus hijos ahora podrán tener unos felices sueños.", TipoMueble.Interior, 85, "camarote", 56565));

        usuarios.add(new Usuario("admin", "adminadmin", TipoUsuario.Administrador));
        usuarios.add(new Usuario("client", "clientclient", TipoUsuario.Cliente));

        Random r = new Random();
        for (int e = 0; e < 8; e++) {
            RegistroVenta venta = new RegistroVenta();
            venta.setCantidad(e);
            venta.setProducto(muebles.get(e));
            venta.setFechaVenta(new Date(r.nextInt()));
            venta.setCiudad("Bogotá");
            registrosVentas.add(venta);
        }
    }

    @Override
    public void create(Object obj) throws OperacionInvalidaException {
        if (obj instanceof Vendedor) {
            Vendedor v = (Vendedor) obj;
            v.setIdentificacion((long) (vendedores.size() + 1));
            vendedores.add(v);
        } else if (obj instanceof Mueble) {
            Mueble m = (Mueble) obj;
            m.setReferencia((long) (muebles.size() + 1));
            muebles.add(m);
        } else if (obj instanceof Usuario) {
            Usuario m = (Usuario) obj;
            for (Usuario us : usuarios) {
                if (us.getLogin().equals(m.getLogin())) {
                    throw new OperacionInvalidaException("El usuario '" + m.getLogin() + "' ya ha sido registrado en el sistema");
                }
                if (us.getDocumento() == m.getDocumento() && us.getTipoDocumento().equals(m.getTipoDocumento())) {
                    throw new OperacionInvalidaException("El usuario con documento '" + m.getDocumento() + "' ya ha sido registrado en el sistema");
                }
            }
            usuarios.add(m);
        } else if (obj instanceof RegistroVenta) {
            registrosVentas.add((RegistroVenta) obj);
        }
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof Vendedor) {
            Vendedor editar = (Vendedor) obj;
            for (int i = 0; i < vendedores.size(); i++) {
                if (vendedores.get(i).getIdentificacion().equals(editar.getIdentificacion())) {
                    vendedores.set(i, editar);
                    break;
                }
            }
        } else if (obj instanceof Mueble) {
            Mueble editar = (Mueble) obj;
            for (int i = 0; i < muebles.size(); i++) {
                if (muebles.get(i).getReferencia().equals(editar.getReferencia())) {
                    muebles.set(i, editar);
                    break;
                }
            }
        } else if (obj instanceof Usuario) {
            Usuario editar = (Usuario) obj;
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getLogin().equals(editar.getLogin())) {
                    usuarios.set(i, editar);
                    break;
                }
            }
        }
    }

    @Override
    public void delete(Object obj) throws OperacionInvalidaException {
        if (obj instanceof Vendedor) {
            Vendedor vendedorABorrar = (Vendedor) obj;
            vendedores.removeIf(v -> v.getIdentificacion().equals(vendedorABorrar.getIdentificacion()));
        } else if (obj instanceof Mueble) {
            Mueble eliminar = (Mueble) obj;
            muebles.removeIf(m -> m.getReferencia().equals(eliminar.getReferencia()));
        } else if (obj instanceof Usuario) {
            Usuario usuarioABorrar = (Usuario) obj;
            for (RegistroVenta rv : registrosVentas) {
                if (rv.getComprador() != null && rv.getComprador().getLogin().equals(usuarioABorrar.getLogin())) {
                    throw new OperacionInvalidaException("El usuario ha realizado compras y por lo tanto no puede ser eliminado del sistema.");
                }
            }
            usuarios.removeIf(u -> u.getLogin().equals(usuarioABorrar.getLogin()));
        } else if (obj instanceof RegistroVenta) {
            registrosVentas.remove((RegistroVenta) obj);
        }
    }

    @Override
    public <T> T findById(Class<T> c, Object id) {
        if (c.equals(Vendedor.class)) {
            return c.cast(vendedores.stream().filter(e -> e.getIdentificacion().equals(Long.parseLong(id.toString()))).findFirst().orElse(null));
        } else if (c.equals(Mueble.class)) {
            return c.cast(muebles.stream().filter(e -> e.getReferencia().equals(Long.parseLong(id.toString()))).findFirst().orElse(null));
        } else if (c.equals(Usuario.class)) {
            return c.cast(usuarios.stream().filter(e -> e.getLogin().equals(id.toString())).findFirst().orElse(null));
        } else if (c.equals(RegistroVenta.class)) {
            return c.cast(registrosVentas.stream().filter(e -> e.getId().equals(Long.parseLong(id.toString()))).findFirst().orElse(null));
        }
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> c) {
        if (c.equals(Vendedor.class)) {
            return (List<T>) vendedores;
        } else if (c.equals(Mueble.class)) {
            return (List<T>) muebles;
        } else if (c.equals(Usuario.class)) {
            return (List<T>) usuarios;
        } else if (c.equals(RegistroVenta.class)) {
            return (List<T>) registrosVentas;
        }
        return new ArrayList<>();
    }
}

