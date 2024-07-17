package ru.mattakvshi.near.controller.community;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import ru.mattakvshi.near.controller.BaseController;

@Tag(name = "CommunityBaseController")
@RestController("/community")
public class CommunityController extends BaseController {
}
