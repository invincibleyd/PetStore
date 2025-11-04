package com.example.petstore;

import com.example.petstore.entity.*;
import com.example.petstore.repository.*;
import com.example.petstore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final AdopterRepository adopterRepository;
    private final AdoptionRequestRepository requestRepository;
    private final UserService userService;

    public DataInitializer(PetRepository petRepository, OwnerRepository ownerRepository,
                           AdopterRepository adopterRepository, AdoptionRequestRepository requestRepository,
                           UserService userService) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.adopterRepository = adopterRepository;
        this.requestRepository = requestRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Always initialize sample data for demo purposes
        if (petRepository.count() == 0) {

            // ✅ Add owner
            Owner owner = new Owner("Alice");
            ownerRepository.save(owner);

            // ✅ Add pets
            Pet buddy = new Pet("Buddy", "Dog", 3, owner.getId());
            buddy.setAvailable(true);
            petRepository.save(buddy);

            Pet mittens = new Pet("Mittens", "Cat", 2, owner.getId());
            mittens.setAvailable(true);
            petRepository.save(mittens);

            Pet goldie = new Pet("Goldie", "Fish", 1, owner.getId());
            goldie.setAvailable(true);
            petRepository.save(goldie);

            Pet tommy = new Pet("Tommy", "Dog", 2, owner.getId());
            tommy.setAvailable(false); // Not available
            petRepository.save(tommy);

            // ✅ Add adopters (with IRS IDs)
            Adopter sam = adopterRepository.findByEmail("sam@example.com")
                    .orElseGet(() -> adopterRepository.save(
                            new Adopter("Sam", "sam@example.com", "12345", "Wonderland", "IRS123SAM")
                    ));

            Adopter ameya = adopterRepository.findByEmail("ameya@example.com")
                    .orElseGet(() -> adopterRepository.save(
                            new Adopter("Ameya", "ameya@example.com", "99999", "Pune", "IRS999AMEYA")
                    ));

            // ✅ Add users for auth
            User admin = new User("Admin", "admin@example.com", "1234567890", "Admin Address");
            admin.setRole(User.Role.ADMIN);
            User user1 = new User("John Doe", "john@example.com", "0987654321", "User Address 1");
            User user2 = new User("Jane Smith", "jane@example.com", "1122334455", "User Address 2");

            // Save users only if they don't exist
            if (!userService.findByEmail("admin@example.com").isPresent()) {
                userService.registerUser(admin);
            }
            if (!userService.findByEmail("john@example.com").isPresent()) {
                userService.registerUser(user1);
            }
            if (!userService.findByEmail("jane@example.com").isPresent()) {
                userService.registerUser(user2);
            }

            // ✅ Verify one adopter and give adoption history
            sam.setVerified(true);
            sam.setAdoptionCount(1);
            adopterRepository.save(sam);

            // ✅ Fetch pets by ID instead of name to avoid duplicates
            // Pet buddy = petRepository.findAll().stream().filter(p -> p.getName().equals("Buddy")).findFirst().orElseThrow();
            // Pet tommy = petRepository.findAll().stream().filter(p -> p.getName().equals("Tommy")).findFirst().orElseThrow();

            // ✅ Create adoption requests
            // Pet buddy = petRepository.findAll().stream().filter(p -> p.getName().equals("Buddy")).findFirst().orElseThrow();
            // Pet tommy = petRepository.findAll().stream().filter(p -> p.getName().equals("Tommy")).findFirst().orElseThrow();

            AdoptionRequest request1 = new AdoptionRequest();
            request1.setPetId(buddy.getId());
            request1.setAdopterId(sam.getId());
            request1.setMessage("I would love to adopt Buddy!");
            requestRepository.save(request1);

            AdoptionRequest request2 = new AdoptionRequest();
            request2.setPetId(tommy.getId());
            request2.setAdopterId(ameya.getId());
            request2.setMessage("Tommy would be perfect for my family!");
            requestRepository.save(request2);

            System.out.println("✅ Sample data initialized successfully with IRS IDs and adoption counts!");
        }
    }
}
