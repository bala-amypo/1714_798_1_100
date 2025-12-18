@RestController
@RequestMapping("/api/compliance-scores")
@Tag(name = "Compliance Scores")
public class ComplianceScoreController {

    private final ComplianceScoreService service;

    public ComplianceScoreController(ComplianceScoreService service) {
        this.service = service;
    }

    @PostMapping("/evaluate/{vendorId}")
    public ComplianceScore evaluate(@PathVariable Long vendorId) {
        return service.evaluateVendor(vendorId);
    }

    @GetMapping("/{vendorId}")
    public ComplianceScore get(@PathVariable Long vendorId) {
        return service.getScore(vendorId);
    }

    @GetMapping
    public List<ComplianceScore> getAll() {
        return service.getAllScores();
    }
}