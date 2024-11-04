package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioRegistroMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioRegistroMockLocal;

import java.util.ArrayList;
import java.util.List;

public class ServicioRegistroMock implements IServicioRegistroMockRemote, IServicioRegistroMockLocal {

    private IServicioPersistenciaMockLocal persistencia;

    public ServicioRegistroMock() {
        persistencia = new ServicioPersistenciaMock();
    }

    @Override
    public void registrar(Usuario u) throws OperacionInvalidaException {
        if (u.getDocumento() != 0) {
            persistencia.create(u);
        } else {
            throw new OperacionInvalidaException("El número de documento no es válido");
        }
    }

    @Override
    public void eliminarCliente(String login) throws OperacionInvalidaException {
        Usuario u = (Usuario) persistencia.findById(Usuario.class, login);
        if (u != null) {
            persistencia.delete(u);
        } else {
            throw new OperacionInvalidaException("No se encontró el usuario con el login proporcionado");
        }
    }

    @Override
    public List<Usuario> darClientes() {
        return persistencia.findAll(Usuario.class);
    }
}

