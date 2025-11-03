package pe.edu.upeu.mspago.DataSeeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.mspago.Entidad.MetodoPago;
import pe.edu.upeu.mspago.Repositorio.MetodoPagoRepositorio;


@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MetodoPagoRepositorio metodoRepo;

    @Override
    @Transactional
    public void run(String... args) {
        if (metodoRepo.count() == 0) {
            metodoRepo.save(build("EFECTIVO", "Pago en efectivo"));
            metodoRepo.save(build("YAPE", "Pago con Yape"));
            metodoRepo.save(build("PLIN", "Pago con Plin"));
            metodoRepo.save(build("TARJETA", "Pago con tarjeta de débito/crédito"));
            metodoRepo.save(build("TRANSFERENCIA", "Transferencia bancaria"));
        }
    }

    private MetodoPago build(String nombre, String desc) {
        MetodoPago mp = new MetodoPago();
        mp.setNombre(nombre);
        mp.setDescripcion(desc);
        mp.setActivo(true);
        return mp;
    }
}