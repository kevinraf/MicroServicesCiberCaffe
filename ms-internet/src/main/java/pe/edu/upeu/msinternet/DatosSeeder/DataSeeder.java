package pe.edu.upeu.msinternet.DatosSeeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.msinternet.Entidad.Cliente;
import pe.edu.upeu.msinternet.Entidad.EstadoMaquina;
import pe.edu.upeu.msinternet.Entidad.Maquina;
import pe.edu.upeu.msinternet.Repositorio.ClienteRepositorio;
import pe.edu.upeu.msinternet.Repositorio.MaquinaRepositorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MaquinaRepositorio maquinaRepo;
    private final ClienteRepositorio clienteRepo;

    @Override
    @Transactional
    public void run(String... args) {
        if (maquinaRepo.count() == 0) {
            Maquina m1 = new Maquina();
            m1.setCodigo("PC01");
            m1.setNombre("Máquina 01");
            m1.setEstado(EstadoMaquina.LIBRE);
            m1.setCostoHora(new BigDecimal("3.50"));
            m1.setDescripcion("PC Gamer Zona A");
            m1.setUltimaActualizacion(LocalDateTime.now());

            Maquina m2 = new Maquina();
            m2.setCodigo("PC02");
            m2.setNombre("Máquina 02");
            m2.setEstado(EstadoMaquina.LIBRE);
            m2.setCostoHora(new BigDecimal("2.50"));
            m2.setDescripcion("PC Estándar Zona B");
            m2.setUltimaActualizacion(LocalDateTime.now());

            maquinaRepo.save(m1);
            maquinaRepo.save(m2);
        }

        if (clienteRepo.count() == 0) {
            Cliente c1 = new Cliente();
            c1.setDni("12345678");
            c1.setNombreCompleto("Juan Pérez");
            c1.setTelefono("987654321");
            c1.setCorreo("juan.perez@example.com");

            Cliente c2 = new Cliente();
            c2.setDni("87654321");
            c2.setNombreCompleto("María García");
            c2.setTelefono("987111222");
            c2.setCorreo("maria.garcia@example.com");

            clienteRepo.save(c1);
            clienteRepo.save(c2);
        }
    }
}