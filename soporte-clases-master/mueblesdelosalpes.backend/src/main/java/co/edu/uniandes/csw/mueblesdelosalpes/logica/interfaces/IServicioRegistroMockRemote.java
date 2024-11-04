

package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;


import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import java.util.List;


public interface IServicioRegistroMockRemote
{
    
    public void registrar(Usuario u)throws OperacionInvalidaException;

  
    public void eliminarCliente(String login) throws OperacionInvalidaException;

    public List<Usuario> darClientes();
}
