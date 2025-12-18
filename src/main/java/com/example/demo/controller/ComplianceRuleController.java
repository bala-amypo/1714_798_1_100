@RestController
@RequestMapping("/api/compliance-rules")
@Tag(name = "Compliance Rules")
public class ComplianceRuleController {

    private final ComplianceRuleService service;

    public ComplianceRuleController(ComplianceRuleService service) {
        this.service = service;
    }

    @PostMapping
    public ComplianceRule create(@RequestBody ComplianceRule r) {
        return service.createRule(r);
    }

    @GetMapping
    public List<ComplianceRule> getAll() {
        return service.getAllRules();
    }  

    @GetMapping("/{id}")
    public ComplianceRule get(@PathVariable Long id) {
        return service.getRule(id);
    }
}